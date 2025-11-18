package com.example.superutility.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun openDocument(context: Context, path: String, mime: String?) {
    val uri = Uri.parse("file://$path")

    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, mime ?: "*/*")
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
