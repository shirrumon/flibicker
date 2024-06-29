package com.vergiltech.flibicker.networking.downloadProcessing

enum class AllowedMimeValue(val value: String) {
    FB2("fb2"),
    FB2_ZIP("fb2.zip"),

    TXT("txt"),
    TXT_ZIP("txt.zip"),

    EPUB("epub"),
    EPUB_ZIP("epub.zip"),

    PDF("pdf"),
    PDF_ZIP("pdf.zip"),

    RTF("rtf"),
    RTF_ZIP("rtf.zip"),
}