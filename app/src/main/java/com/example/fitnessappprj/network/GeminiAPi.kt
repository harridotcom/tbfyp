package com.example.fitnessappprj.network

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun geminiApi(prompt: String): String {
    val apiKey = "YOUR_API_KEY"
    val url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=$apiKey"

    val client = OkHttpClient()

    val jsonMediaType = "application/json".toMediaType()
    val requestBody = """
        {
            "contents": [{
                "parts": [{"text": "$prompt"}]
            }]
        }
    """.trimIndent().toRequestBody(jsonMediaType)

    val request = Request.Builder()
        .url(url)
        .post(requestBody)
        .addHeader("Content-Type", "application/json")
        .build()

    return withContext(Dispatchers.IO) {
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            response.body?.string() ?: "No response"
        } else {
            "Error: ${response.code} - ${response.message}"
        }
    }
}
