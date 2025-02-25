package com.example.fitnessapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FitMapScreen() {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Card for the Map View (Placeholder UI)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.LightGray)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = "Map Placeholder",
                        modifier = Modifier.align(androidx.compose.ui.Alignment.Center),
                        fontSize = 18.sp,
                        color = Color.DarkGray
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Nearby Hospitals & Doctors Section
            Text(text = "Nearby Hospitals & Doctors", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(10.dp))

            repeat(3) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Hospital/Doctor Name", fontSize = 16.sp, color = Color.Black)
                        Text(text = "Address: Some location", fontSize = 14.sp, color = Color.Gray)
                        Text(text = "Distance: 1.5 km", fontSize = 14.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}
