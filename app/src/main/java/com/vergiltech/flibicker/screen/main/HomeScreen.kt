package com.vergiltech.flibicker.screen.main

import android.text.Html
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vergiltech.flibicker.networking.api.parser.CommonParser
import com.vergiltech.flibicker.screen.main.partials.Search
import com.vergiltech.flibicker.screen.main.vm.HomeViewModel

@Composable
fun HomeScreen (
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val bookList by homeViewModel.bookSearchResult.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        Search(homeViewModel = homeViewModel)

        bookList?.let {
            val list = it as CommonParser.FlibustaBookList

            Spacer(modifier = Modifier.height(25.dp))
            list.entries?.forEach {  bookEntry ->
                Column(modifier = Modifier
                    .padding(5.dp)
                    .border(1.dp, Color.Black)
                    .fillMaxWidth()) {
                    Row{
                        Text(text = "Title: ${bookEntry.title}")
                    }
                    Spacer(modifier = Modifier.height(2.dp))

                    bookEntry.author?.forEach { author ->
                        Row{
                            Text(text = "Author name: ${author.name}")
                        }
                        Spacer(modifier = Modifier.height(2.dp))

                        Row{
                            Text(text = "Author uri: ${author.uri}")
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                    }

                    Row{
                        Text(text = "Description: ${Html.fromHtml(bookEntry.content,
                            Html.FROM_HTML_MODE_LEGACY) }")
                    }
                    Spacer(modifier = Modifier.height(2.dp))

                    bookEntry.link?.forEach { link ->
                        link.type?.let {
                            if(link.type == "application/fb2+zip") {
                                Button(onClick = {
                                    Log.e("download: ", "http://flibusta.site${link.href}")
                                    homeViewModel.startBookDownload(
                                        url = "https://flibusta.site${link.href}",
                                        fileName = "Book.fb2.zip",
                                        mime = it
                                    )
                                }) {
                                    Text(text = "Download book")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}