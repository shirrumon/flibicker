package com.vergiltech.flibicker.networking.downloadProcessing

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Environment
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DownloadProcessing @Inject constructor(
    @ApplicationContext private val context: Context
) {
    @SuppressLint("InlinedApi")
    fun downLoadFile(
        url: String,
        fileName: String,
        description: String,
        mime: String
    ) {
        val downloadManager by lazy {
            context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        }

        val downloadRequest = DownloadManager.Request(Uri.parse(url))
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)
            .setMimeType(mime)
            .setTitle(fileName)
            .setDescription(description)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

        val downloadId = downloadManager.enqueue(downloadRequest)
    }
}