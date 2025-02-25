package com.example.fitnessappprj

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.fitnessapp.navigation.FitNavigation
import com.example.fitnessappprj.viewmodel.FitnessViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val fitnessViewModel = FitnessViewModel()
        setContent {
            FitNavigation(fitnessViewModel = fitnessViewModel)
        }
    }
}

