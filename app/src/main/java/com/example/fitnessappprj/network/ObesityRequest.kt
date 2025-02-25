package com.example.fitnessappprj.network

data class ObesityRequest(
    val Gender: String,
    val Age: Int,
    val Height: Double,
    val Weight: Int,
    val family_history_with_overweight: String,
    val FAVC: String,
    val FCVC: Int,
    val NCP: Int,
    val CAEC: String,
    val SMOKE: String,
    val CH2O: Int,
    val SCC: String,
    val FAF: Int,
    val TUE: Int,
    val CALC: String,
    val MTRANS: String
)
