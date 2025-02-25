package com.example.fitnessappprj.network

data class DiabetesRequest(
    val Pregnancies: Int,
    val Glucose: Int,
    val BloodPressure: Int,
    val SkinThickness: Int,
    val Insulin: Int,
    val BMI: Double,
    val DiabetesPedigreeFunction: Double,
    val Age: Int
)
