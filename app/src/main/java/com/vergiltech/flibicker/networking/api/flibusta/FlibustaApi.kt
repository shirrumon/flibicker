package com.vergiltech.flibicker.networking.api.flibusta

import com.vergiltech.flibicker.networking.api.flibusta.mapper.BookMapper.serializeBookSearch
import com.vergiltech.flibicker.networking.api.parser.CommonParser
import com.vergiltech.flibicker.networking.core.Ktor
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import java.net.URLEncoder
import javax.inject.Inject


class FlibustaApi @Inject constructor(
    ktorConfig: Ktor,
) {
    private val url = "http://flibusta.site/opds"
    private val client = ktorConfig.client()

    suspend fun searchBook(bookName: String): CommonParser? {
        val response: HttpResponse = client.get(getSearchBookPattern(bookName))
        val data = serializeBookSearch(response.bodyAsText())

        return data
    }

    private fun getSearchBookPattern(bookName: String) =
        "$url/opensearch?searchTerm=${URLEncoder.encode(bookName.lowercase(), "UTF-8")}&amp;searchType=books"
}