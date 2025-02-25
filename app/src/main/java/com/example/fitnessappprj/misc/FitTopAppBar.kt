package com.example.fitnessapp.misc

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitnessapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FitTopAppBar(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    CenterAlignedTopAppBar(
        modifier = modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = colorResource(R.color.base)
        ),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(R.drawable.heart),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(32.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "MyFitnessPal",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        actions = {
            IconButton(onClick = {
                navController.navigate("firstAid")
            }) {
                Icon(
                    painter = painterResource(R.drawable.medical),
                    contentDescription = "New Icon",
                    modifier = Modifier.size(25.dp),
                    tint = Color.Black
                )
            }

            IconButton(onClick = {
                navController.navigate("ProfileScreen")
            }) {
                Icon(
                    painter = painterResource(R.drawable.user),
                    contentDescription = "Profile",
                    modifier = Modifier.size(25.dp),
                    tint = Color.Black
                )
            }
        }
    )
}
