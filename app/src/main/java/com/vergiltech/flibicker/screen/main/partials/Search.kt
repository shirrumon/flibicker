package com.vergiltech.flibicker.screen.main.partials

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vergiltech.flibicker.screen.main.vm.HomeViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search(homeViewModel: HomeViewModel) {
    val searchText by homeViewModel.searchText.collectAsState()
    val isSearching by homeViewModel.isSearching.collectAsState()
    val countriesList by homeViewModel.countriesList.collectAsState()

    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Top) {
        SearchBar(
            query = searchText,
            onQueryChange = homeViewModel::onSearchTextChange,
            onSearch = homeViewModel::onSearchTextChange,
            active = isSearching,
            onActiveChange = { homeViewModel.onToogleSearch() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            LazyColumn {
                items(countriesList) { country ->
                    Text(
                        text = country,
                        modifier = Modifier.padding(
                            start = 8.dp,
                            top = 4.dp,
                            end = 8.dp,
                            bottom = 4.dp)
                    )
                }
            }
        }
    }
}