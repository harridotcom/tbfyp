package com.example.fitnessapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitnessapp.R
import com.example.fitnessapp.misc.FitTopAppBar


private val CardColor = Color(0xFFFFFFFF)
private val TextColor = Color(0xFF4A4A4A)
private val BorderColor = Color(0xFFB0B0B0)

@Composable
fun FitHomeScreen(navController: NavController) {
    Scaffold(
        topBar = { FitTopAppBar(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.backgroundColor))
                .padding(24.dp)
                .padding(paddingValues)
        ) {
            Text(
                text = "Hello, User 👋",
                color = TextColor,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "What would you like to check today?",
                color = TextColor.copy(alpha = 0.8f),
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 6.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))

            HomeScreenButton(
                icon = painterResource(R.drawable.heart),
                title = "Health Assessment",
                subtitle = "AI-designed Diabetes Predictor",
                onClick = { navController.navigate("healthAssessment") }
            )

            HomeScreenButton(
                icon = painterResource(R.drawable.healthy_food),
                title = "Diet Plan",
                subtitle = "Personalized Nutrition Advice",
                onClick = { navController.navigate("dietForm") }
            )

            HomeScreenButton(
                icon = painterResource(R.drawable.running),
                title = "Workout Plan",
                subtitle = "AI-Designed Fitness Plan",
                onClick = { navController.navigate("workoutForm") }
            )

            HomeScreenButton(
                icon = painterResource(R.drawable.placeholder),
                title = "Map",
                subtitle = "Find Near By Doctors",
                onClick = { navController.navigate("maps") }
            )
        }
    }
}

@Composable
fun HomeScreenButton(
    icon: Painter,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(R.color.CardColor), shape = RoundedCornerShape(16.dp))
            .border(1.dp, BorderColor, RoundedCornerShape(16.dp))
            .padding(16.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = title,
            tint = TextColor,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = title,
                color = TextColor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = subtitle,
                color = TextColor.copy(alpha = 0.7f),
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(R.drawable._dumbel),
            contentDescription = "Navigate",
            tint = TextColor,
            modifier = Modifier.size(24.dp)
        )
    }
    Spacer(modifier = Modifier.height(20.dp))
}