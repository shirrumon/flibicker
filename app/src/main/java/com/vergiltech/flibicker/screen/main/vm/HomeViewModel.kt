package com.vergiltech.flibicker.screen.main.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vergiltech.flibicker.networking.api.flibusta.FlibustaApi
import com.vergiltech.flibicker.networking.api.parser.CommonParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val flibustaApi: FlibustaApi
): ViewModel() {
    private val _bookSearchResult = MutableStateFlow<CommonParser?>(null)
    val bookSearchResult = _bookSearchResult.asStateFlow()

    fun searchBook(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _bookSearchResult.value = flibustaApi.searchBook(query)
        }
    }
}