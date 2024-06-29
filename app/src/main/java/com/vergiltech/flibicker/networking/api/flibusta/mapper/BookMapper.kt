package com.vergiltech.flibicker.networking.api.flibusta.mapper

import android.util.Log
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Serializer
import org.simpleframework.xml.core.Persister
import java.io.StringReader

object BookMapper {
    fun serializeBookSearch(responseString: String?): Root? {
        try {
            val serializer: Serializer = Persister()
            val reader = StringReader(responseString)

            return serializer.read(Root::class.java, reader, false)
        } catch (e: Exception) {
            Log.e("Flibusta deserialize error: ", e.message.toString())
        }

        return null
    }

    @org.simpleframework.xml.Root(strict = false, name = "feed")
    data class Root @JvmOverloads constructor(
        @field:ElementList(name = "entry", inline = true, required = false)
        var entries: List<Entry>? = null
    )

    @org.simpleframework.xml.Root(strict = false, name = "entry")
    data class Entry @JvmOverloads constructor(
        @field:Element(name = "title")
        @param:Element(name = "title")
        var title: String? = null,

        @field:ElementList(name = "author", inline = true, required = false)
        var author: List<Author>? = null,

        @field:Element(name = "content", required = false)
        @param:Element(name = "content", required = false)
        var content: String? = null,

        @field:ElementList(name = "link", inline = true, required = false)
        var link: List<Link>? = null,

        @field:ElementList(name = "category", inline = true, required = false)
        var category: List<Category>? = null
    )

    @org.simpleframework.xml.Root(strict = false, name = "category")
    data class Category @JvmOverloads constructor(
        @field:Attribute(name = "term")
        var term: String? = null,

        @field:Attribute(name = "label")
        var label: String? = null
    )

    @org.simpleframework.xml.Root(strict = false, name = "link")
    data class Link @JvmOverloads constructor(
        @field:Attribute(name = "href")
        var href: String? = null,

        @field:Attribute(name = "type")
        var type: String? = null,

        @field:Attribute(name = "rel")
        var rel: String? = null
    )

    @org.simpleframework.xml.Root(strict = false, name = "author")
    data class Author @JvmOverloads constructor(
        @field:Element(name = "name")
        @param:Element(name = "name")
        var name: String? = null,

        @field:Element(name = "uri")
        @param:Element(name = "uri")
        var uri: String? = null
    )
}