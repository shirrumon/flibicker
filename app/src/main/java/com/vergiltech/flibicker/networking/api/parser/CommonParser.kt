package com.vergiltech.flibicker.networking.api.parser

import com.vergiltech.flibicker.networking.api.flibusta.mapper.BookMapper.Entry
import org.simpleframework.xml.ElementList

sealed class CommonParser {
    @org.simpleframework.xml.Root(strict = false, name = "feed")
    data class FlibustaBookList @JvmOverloads constructor(
        @field:ElementList(name = "entry", inline = true, required = false)
        var entries: List<Entry>? = null
    ): CommonParser()
}