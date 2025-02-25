package com.example.fitnessappprj.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fitnessappprj.network.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FitnessViewModel : ViewModel() {

    private val _diabetesResponse = MutableLiveData<FitDiabetesPrediction>()
    val diabetesResponse: LiveData<FitDiabetesPrediction> get() = _diabetesResponse

    private val _obesityResponse = MutableLiveData<FitObesityPrediction>()
    val obesityResponse: LiveData<FitObesityPrediction> get() = _obesityResponse

    private val _thyroidResponse = MutableLiveData<FitThyroidResponse>()
    val thyroidResponse: LiveData<FitThyroidResponse> get() = _thyroidResponse

    fun predictDiabetes(request: DiabetesRequest) {
        RetrofitClient.instance.getDiabetesPrediction(request).enqueue(object : Callback<FitDiabetesPrediction> {
            override fun onResponse(call: Call<FitDiabetesPrediction>, response: Response<FitDiabetesPrediction>) {
                if (response.isSuccessful) {
                    _diabetesResponse.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<FitDiabetesPrediction>, t: Throwable) {
                _diabetesResponse.postValue(FitDiabetesPrediction(0, t.message))
            }
        })
    }

    fun predictObesity(request: ObesityRequest) {
        RetrofitClient.instance.getObesityPrediction(request).enqueue(object : Callback<FitObesityPrediction> {
            override fun onResponse(call: Call<FitObesityPrediction>, response: Response<FitObesityPrediction>) {
                if (response.isSuccessful) {
                    _obesityResponse.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<FitObesityPrediction>, t: Throwable) {
                _obesityResponse.postValue(FitObesityPrediction(0, t.message))
            }
        })
    }

    fun predictThyroid(request: ThyroidRequest) {
        RetrofitClient.instance.getThyroidPrediction(request).enqueue(object : Callback<FitThyroidResponse> {
            override fun onResponse(call: Call<FitThyroidResponse>, response: Response<FitThyroidResponse>) {
                if (response.isSuccessful) {
                    _thyroidResponse.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<FitThyroidResponse>, t: Throwable) {
                _thyroidResponse.postValue(FitThyroidResponse(0, t.message))
            }
        })
    }
}
