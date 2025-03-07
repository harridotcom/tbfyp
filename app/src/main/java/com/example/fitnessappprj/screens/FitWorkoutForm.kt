package com.example.fitnessapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitnessapp.R
import com.example.fitnessapp.misc.FitResultScreen
import com.example.fitnessapp.misc.FitTopAppBar
import com.example.fitnessapp.misc.testthings
import com.example.fitnessappprj.network.geminiApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun FitWorkoutForm(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    var fitnessGoal by remember { mutableStateOf("") }
    var workoutFrequency by remember { mutableStateOf("") }
    var availableEquipment by remember { mutableStateOf("") }
    var healthConditions by remember { mutableStateOf("") }
    var injuries by remember { mutableStateOf("") }
    var workoutDuration by remember { mutableStateOf("") }

    // Dropdown states
    var selectedFitnessLevel by remember { mutableStateOf("") }
    var expandedFitnessLevel by remember { mutableStateOf(false) }
    val fitnessLevels = listOf("Beginner", "Intermediate", "Advanced")

    var selectedWorkoutType by remember { mutableStateOf("") }
    var expandedWorkoutType by remember { mutableStateOf(false) }
    val workoutTypes = listOf("Cardio", "Strength Training", "Yoga", "HIIT", "Pilates")

    var selectedActivityLevel by remember { mutableStateOf("") }
    var expandedActivityLevel by remember { mutableStateOf(false) }
    val activityLevels = listOf("Sedentary", "Lightly Active", "Moderately Active", "Highly Active")

    var showResultScreen by remember { mutableStateOf(false) }

    var workoutPlan by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(isLoading) {
        if (isLoading) {
            workoutPlan = withContext(Dispatchers.IO) {
                geminiApi(
                    "Generate a personalized workout plan for a ${selectedFitnessLevel.lowercase()} " +
                            "individual with a goal of $fitnessGoal. The user prefers $selectedWorkoutType " +
                            "workouts, can work out $workoutFrequency times per week, has a duration of " +
                            "$workoutDuration minutes per session, and has the following constraints: " +
                            "Health conditions: $healthConditions, Injuries: $injuries."
                )
            }
            isLoading = false
            showResultScreen = true
        }
    }



    Scaffold(
        topBar = { FitTopAppBar(navController = navController) },
        content = { paddingValues ->

            if (showResultScreen) {
                FitResultScreen(
                    onDismiss = { showResultScreen = false },
                    text = testthings.workoutPlan,
                    planType = "Your Workout Plan"
                )
            }

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        Image(
                            painter = painterResource(id = R.drawable.running),
                            contentDescription = "Running icon",
                            modifier = Modifier
                                .size(80.dp)
                                .padding(start = 10.dp)
                                .scale(scaleX = -1f, scaleY = 1f)
                        )
                    }

                    // Fitness Level Dropdown
                    item {
                        ExposedDropdownMenuBox(
                            expanded = expandedFitnessLevel,
                            onExpandedChange = { expandedFitnessLevel = !expandedFitnessLevel }
                        ) {
                            OutlinedTextField(
                                value = selectedFitnessLevel,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Current Fitness Level", fontSize = 12.sp) },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.fitness),
                                        contentDescription = "Fitness Level",
                                        modifier = Modifier.size(20.dp)
                                    )
                                },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp),
                                textStyle = MaterialTheme.typography.bodyMedium,
                                trailingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                                        contentDescription = "Dropdown",
                                        modifier = Modifier.rotate(if (expandedFitnessLevel) 180f else 0f)
                                    )
                                }
                            )
                            ExposedDropdownMenu(
                                expanded = expandedFitnessLevel,
                                onDismissRequest = { expandedFitnessLevel = false },
                                modifier = Modifier.exposedDropdownSize()
                            ) {
                                fitnessLevels.forEach { level ->
                                    DropdownMenuItem(
                                        text = { Text(level) },
                                        onClick = {
                                            selectedFitnessLevel = level
                                            expandedFitnessLevel = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = fitnessGoal,
                                onValueChange = { fitnessGoal = it },
                                label = { Text("Fitness Goals", fontSize = 12.sp) },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.goal),
                                        contentDescription = "Goal",
                                        modifier = Modifier.size(20.dp)
                                    )
                                },
                                modifier = Modifier.weight(1f),
                                singleLine = true,
                                textStyle = MaterialTheme.typography.bodyMedium
                            )

                            OutlinedTextField(
                                value = workoutFrequency,
                                onValueChange = { workoutFrequency = it },
                                label = { Text("Workout Frequency", fontSize = 12.sp) },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.goal),
                                        contentDescription = "Frequency",
                                        modifier = Modifier.size(20.dp)
                                    )
                                },
                                modifier = Modifier.weight(1f),
                                singleLine = true,
                                textStyle = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    item {
                        OutlinedTextField(
                            value = workoutDuration,
                            onValueChange = { workoutDuration = it },
                            label = { Text("Workout Duration", fontSize = 12.sp) },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.goal),
                                    contentDescription = "Duration",
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            singleLine = true,
                            textStyle = MaterialTheme.typography.bodyMedium
                        )
                    }

                    // Workout Type Dropdown
                    item {
                        ExposedDropdownMenuBox(
                            expanded = expandedWorkoutType,
                            onExpandedChange = { expandedWorkoutType = !expandedWorkoutType }
                        ) {
                            OutlinedTextField(
                                value = selectedWorkoutType,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Preferred Workouts", fontSize = 12.sp) },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.weight),
                                        contentDescription = "Workout Type",
                                        modifier = Modifier.size(20.dp)
                                    )
                                },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp),
                                textStyle = MaterialTheme.typography.bodyMedium,
                                trailingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                                        contentDescription = "Dropdown",
                                        modifier = Modifier.rotate(if (expandedWorkoutType) 180f else 0f)
                                    )
                                }
                            )
                            ExposedDropdownMenu(
                                expanded = expandedWorkoutType,
                                onDismissRequest = { expandedWorkoutType = false },
                                modifier = Modifier.exposedDropdownSize()
                            ) {
                                workoutTypes.forEach { type ->
                                    DropdownMenuItem(
                                        text = { Text(type) },
                                        onClick = {
                                            selectedWorkoutType = type
                                            expandedWorkoutType = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    item {
                        OutlinedTextField(
                            value = injuries,
                            onValueChange = { injuries = it },
                            label = { Text("Physical Injuries", fontSize = 12.sp) },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.injury),
                                    contentDescription = "Injuries",
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            singleLine = true,
                            textStyle = MaterialTheme.typography.bodyMedium
                        )
                    }

                    item {
                        OutlinedTextField(
                            value = healthConditions,
                            onValueChange = { healthConditions = it },
                            label = { Text("Health Conditions", fontSize = 12.sp) },
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Favorite,
                                    contentDescription = "Health",
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            singleLine = true,
                            textStyle = MaterialTheme.typography.bodyMedium
                        )
                    }

                    // Activity Level Dropdown
                    item {
                        ExposedDropdownMenuBox(
                            expanded = expandedActivityLevel,
                            onExpandedChange = { expandedActivityLevel = !expandedActivityLevel }
                        ) {
                            OutlinedTextField(
                                value = selectedActivityLevel,
                                onValueChange = {},
                                readOnly = true,
                                label = { Text("Activity Level", fontSize = 12.sp) },
                                leadingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.fitness),
                                        contentDescription = "Activity Level",
                                        modifier = Modifier.size(20.dp)
                                    )
                                },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                                    .padding(horizontal = 10.dp),
                                textStyle = MaterialTheme.typography.bodyMedium,
                                trailingIcon = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_arrow_drop_down_24),
                                        contentDescription = "Dropdown",
                                        modifier = Modifier.rotate(if (expandedActivityLevel) 180f else 0f)
                                    )
                                }
                            )
                            ExposedDropdownMenu(
                                expanded = expandedActivityLevel,
                                onDismissRequest = { expandedActivityLevel = false },
                                modifier = Modifier.exposedDropdownSize()
                            ) {
                                activityLevels.forEach { level ->
                                    DropdownMenuItem(
                                        text = { Text(level) },
                                        onClick = {
                                            selectedActivityLevel = level
                                            expandedActivityLevel = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { isLoading = true },
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.base))
                        ) {
                            Text(text = if (isLoading) "Generating..." else "Generate Plan", fontSize = 18.sp)
                        }
                    }
                }
            }
        }
    )
}
