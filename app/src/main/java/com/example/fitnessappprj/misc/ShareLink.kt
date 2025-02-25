package com.example.fitnessapp.misc

import android.content.Context
import android.content.Intent
import android.widget.Toast

fun shareLink(context: Context, link: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, link) // The link you want to share
    }

    try {
        context.startActivity(Intent.createChooser(intent, "Share link via"))
    } catch (e: Exception) {
        Toast.makeText(context, "No apps available to share", Toast.LENGTH_SHORT).show()
    }
}