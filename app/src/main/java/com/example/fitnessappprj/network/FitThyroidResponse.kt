package com.example.fitnessappprj.network

data class FitThyroidResponse(
    val thyroid_prediction: Int,
    val error: String? = null
)