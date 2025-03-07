package com.example.fitnessapp.navigation

import IntroScreen
import androidx.collection.floatIntMapOf
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fitnessapp.screens.DiabetesForm
import com.example.fitnessapp.screens.FirstAid
import com.example.fitnessapp.screens.FitDietForm
import com.example.fitnessapp.screens.FitHomeScreen
import com.example.fitnessapp.screens.FitLoginPage
import com.example.fitnessapp.screens.FitMapScreen
import com.example.fitnessapp.screens.FitProfilePage
import com.example.fitnessapp.screens.FitSignUpPage
import com.example.fitnessapp.screens.FitWorkoutForm
import com.example.fitnessapp.screens.HealthAssessment
import com.example.fitnessapp.screens.ObesityForm
import com.example.fitnessapp.screens.ThyroidForm
import com.example.fitnessappprj.viewmodel.FitnessViewModel

@Composable
fun FitNavigation(modifier: Modifier = Modifier, fitnessViewModel: FitnessViewModel) {
    val navController = rememberNavController()
    NavHost(
        startDestination = "IntroScreen",
        navController = navController
    ){
        composable("home"){
            FitHomeScreen(navController = navController)
        }
        composable("dietForm"){
            FitDietForm(navController = navController)
        }
        composable("workoutForm"){
            FitWorkoutForm(navController = navController)
        }
        composable("diabetesForm"){
            DiabetesForm(navController = navController, fitnessViewModel = fitnessViewModel)
        }
        composable("thyroidForm"){
            ThyroidForm(navController = navController, fitnessViewModel = fitnessViewModel)
        }
        composable("obesityForm"){
            ObesityForm(
                navController = navController,
                fitnessViewModel = fitnessViewModel
            )
        }
        composable("healthAssessment"){
            HealthAssessment(navController = navController)
        }
        composable("IntroScreen"){
            IntroScreen(navController = navController)
        }
        composable("ProfileScreen"){
            FitProfilePage(navController = navController)
        }
        composable("login"){
            FitLoginPage(navController = navController)
        }
        composable("signup"){
            FitSignUpPage(navController = navController)
        }
        composable("firstAid"){
            FirstAid()
        }
        composable("maps"){
            FitMapScreen()
        }
    }
}