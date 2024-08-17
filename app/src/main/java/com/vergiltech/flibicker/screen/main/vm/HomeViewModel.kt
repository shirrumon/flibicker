package com.vergiltech.flibicker.screen.main.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vergiltech.flibicker.networking.api.flibusta.FlibustaApi
import com.vergiltech.flibicker.networking.api.parser.CommonParser
import com.vergiltech.flibicker.networking.downloadProcessing.DownloadProcessing
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val flibustaApi: FlibustaApi,
    private val downloadProcessing: DownloadProcessing
) : ViewModel() {
    private val _uiState: MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState.DEFAULT)
    private val _processingState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val _screenCommunicateString: MutableStateFlow<CommunicateState> =
        MutableStateFlow(CommunicateState.DEFAULT)
    private val _searchResult: MutableStateFlow<CommonParser?> = MutableStateFlow(null)

    /**
     * public realization section
     */
    val searchResult = _searchResult.asStateFlow()
    val processingState = _processingState.asStateFlow()
    val screenCommunicateString = _screenCommunicateString.asStateFlow()

    fun searchBook(query: String) {
        _uiState.value = ScreenState.LOADING
        viewModelScope.launch(Dispatchers.IO) {
            val responseData = flibustaApi.searchBook(query)

            if(responseData is CommonParser.FlibustaBookList) {
                if(responseData.entries != null) {
                    _searchResult.value = responseData
                    _uiState.value = ScreenState.SUCCESS
                } else {
                    _uiState.value = ScreenState.NO_DATA_FIND
                }
            }
        }
    }

    /**
     * Download manager section
     */
    fun startBookDownload(url: String, fileName: String, mime: String) {
        viewModelScope.launch(Dispatchers.IO) {
            downloadProcessing.downLoadFile(
                url = url,
                fileName = fileName,
                description = "Book downloaded is started now",
                mime = mime
            )
        }
    }

    fun revertToDefault() {
        _uiState.value = ScreenState.DEFAULT
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.collect { collectableState ->
                when (collectableState) {
                    ScreenState.LOADING -> {
                        _processingState.value = true
                    }

                    ScreenState.SUCCESS -> {
                        _processingState.value = false
                        _screenCommunicateString.value = CommunicateState.PAGE
                    }

                    ScreenState.NO_DATA_FIND -> {
                        _processingState.value = false
                        _screenCommunicateString.value = CommunicateState.EMPTY
                    }

                    ScreenState.DEFAULT -> {
                        _processingState.value = false
                        _screenCommunicateString.value = CommunicateState.DEFAULT
                        _searchResult.value = null
                    }
                }
            }
        }
    }
}

private enum class ScreenState {
    DEFAULT,
    LOADING,
    SUCCESS,
    NO_DATA_FIND,
}

enum class CommunicateState {
    DEFAULT,
    EMPTY,
    PAGE
}