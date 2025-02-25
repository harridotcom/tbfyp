package com.example.fitnessapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitnessapp.R
import com.example.fitnessapp.misc.FitTopAppBar

@Composable
fun HealthAssessment(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Scaffold (
        topBar = { FitTopAppBar(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.backgroundColor))
                .padding(24.dp)
                .padding(paddingValues)
        ) {
            Text(
                text = "Health Assessment",
                color = colorResource(id = R.color.TextColor),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Welcome to the Health Assessment page, where we provide AI-powered tools to help predict and manage various health conditions. Whether you’re concerned about diabetes, thyroid health, or obesity, our advanced models are designed to provide accurate predictions and guide you toward a healthier lifestyle.",
                color = colorResource(id = R.color.TextColor),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Explore our specialized prediction tools:",
                color = colorResource(id = R.color.TextColor),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Using LazyColumn for better performance with dynamic lists
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    HomeScreenButton(
                        icon = painterResource(R.drawable.blood_test),
                        title = "Diabetes Prediction",
                        subtitle = "AI-designed Diabetes Predictor",
                        onClick = { navController.navigate("diabetesForm") }
                    )
                }

                item {
                    HomeScreenButton(
                        icon = painterResource(R.drawable.endocrine),
                        title = "Thyroid Prediction",
                        subtitle = "AI-designed Thyroid Predictor",
                        onClick = { navController.navigate("thyroidForm") }
                    )
                }

                item {
                    HomeScreenButton(
                        icon = painterResource(R.drawable.fat),
                        title = "Obesity Prediction",
                        subtitle = "AI-designed Obesity Predictor",
                        onClick = { navController.navigate("obesityForm") }
                    )
                }
            }
        }
    }
}

