package com.example.fitnessappprj.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("predict/diabetes/")
    fun getDiabetesPrediction(@Body request: DiabetesRequest): Call<FitDiabetesPrediction>

    @Headers("Content-Type: application/json")
    @POST("predict/obesity/")
    fun getObesityPrediction(@Body request: ObesityRequest): Call<FitObesityPrediction>

    @Headers("Content-Type: application/json")
    @POST("predict/thyroid/")
    fun getThyroidPrediction(@Body request: ThyroidRequest): Call<FitThyroidResponse>

    @GET
    fun getGeminiResponse(@Body request: GeminiRequest): Call<GeminiResponse>
}
