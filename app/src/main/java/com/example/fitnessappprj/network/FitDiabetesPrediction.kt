package com.example.fitnessappprj.network

data class FitDiabetesPrediction(
    val diabetes_prediction: Int,
    val error: String? = null
)