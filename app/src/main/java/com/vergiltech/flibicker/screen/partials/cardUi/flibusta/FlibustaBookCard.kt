package com.vergiltech.flibicker.screen.partials.cardUi.flibusta

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vergiltech.flibicker.networking.api.flibusta.mapper.BookMapper.Author
import com.vergiltech.flibicker.networking.api.flibusta.mapper.BookMapper.Category
import com.vergiltech.flibicker.networking.api.flibusta.mapper.BookMapper.Link
import com.vergiltech.flibicker.networking.downloadProcessing.AllowedMimeValue
import com.vergiltech.flibicker.screen.main.vm.HomeViewModel
import kotlin.math.min

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ElevatedBookCard(
    homeViewModel: HomeViewModel,
    bookTitle: String,
    authors: List<Author>?,
    category: List<Category>?,
    content: String?,
    links: List<Link>?
) {
    val baseFlibustaUrl = "https://flibusta.site"

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            text = bookTitle,
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Start,
        )

        Column(modifier = Modifier
            .padding(top = 10.dp, bottom = 10.dp, start = 5.dp)
            .fillMaxWidth()) {
            category?.let { category ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Text(text = "Category: ")
                        category.forEach {
                            Text(text = "- ${it.label}", modifier = Modifier.padding(start = 5.dp, top = 2.dp))
                        }
                    }
                }
            }

            authors?.let { authors ->
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth())  {
                    Column {
                        Text(text = "Authors:")
                        Column {
                            authors.forEach {
                                Text(text = "- ${it.name}", modifier = Modifier.padding(start = 5.dp, top = 2.dp))
                            }
                        }
                    }
                }
            }

            content?.let { content ->
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth())  {
                    var description = content

                    if(description.length >= 120) {
                        description = description.substring(0, min(description.length, 120)) + "..."
                    }

                    Text(text = description)
                }
            }

            links?.let { links ->
                Spacer(modifier = Modifier.height(10.dp))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    links.forEach { link ->
                        link.type?.let { mime ->
                            val bookExtension = mime
                                .substringAfter("/")
                                .replace("+", ".")

                            if(validateMimeType(bookExtension)) {
                                Button(
                                    modifier = Modifier.padding(3.dp),
                                    onClick = { homeViewModel.startBookDownload(
                                    url = baseFlibustaUrl+link.href,
                                    fileName = "$bookTitle.$bookExtension",
                                    mime = mime
                                ) }) {
                                    Text("Download $bookExtension")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun validateMimeType(mime: String): Boolean {
    return when(mime) {
        AllowedMimeValue.FB2.value,
        AllowedMimeValue.FB2_ZIP.value,
        AllowedMimeValue.TXT.value,
        AllowedMimeValue.TXT_ZIP.value,
        AllowedMimeValue.EPUB.value,
        AllowedMimeValue.EPUB_ZIP.value,
        AllowedMimeValue.PDF.value,
        AllowedMimeValue.PDF_ZIP.value,
        AllowedMimeValue.RTF.value,
        AllowedMimeValue.RTF_ZIP.value,
        -> true
        else -> false
    }
}