package com.vergiltech.flibicker.screen.main

import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vergiltech.flibicker.networking.api.parser.CommonParser
import com.vergiltech.flibicker.screen.main.partials.SearchBar
import com.vergiltech.flibicker.screen.main.vm.HomeViewModel
import com.vergiltech.flibicker.screen.partials.cardUi.flibusta.ElevatedBookCard
import com.vergiltech.flibicker.ui.parts.Spacers.LargeVerticalSpacer
import com.vergiltech.flibicker.ui.theme.PaddingValues

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val bookList by homeViewModel.bookSearchResult.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(PaddingValues.small)
    ) {
        Row(modifier = Modifier
            .weight(1f)
            .fillMaxWidth()) {
            var providedSearchString = ""

            SearchBar(
                height = 60.dp,
                hint = "Search by book name",
                onTextChange = {
                    providedSearchString = it
                },
                onSearchClicked = {
                    homeViewModel.searchBook(providedSearchString)
                }
            )
        }

        Row(modifier = Modifier
            .weight(9f)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())) {
            bookList?.let { list ->
                LargeVerticalSpacer()

                if (list is CommonParser.FlibustaBookList) {
                    list.entries?.forEach { entry ->
                        ElevatedBookCard(
                            homeViewModel = homeViewModel,
                            bookTitle = entry.title ?: "",
                            authors = entry.author,
                            category = entry.category,
                            content = Html.fromHtml(entry.content, FROM_HTML_MODE_LEGACY)
                                .toString(),
                            links = entry.link
                        )
                    }
                }
            }
        }
    }
}