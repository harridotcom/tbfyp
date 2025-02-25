package com.example.fitnessapp.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitnessapp.R
import com.example.fitnessapp.misc.FitTopAppBar
import com.example.fitnessapp.misc.ShowPopupDialog
import com.example.fitnessappprj.network.DiabetesRequest
import com.example.fitnessappprj.network.FitDiabetesPrediction
import com.example.fitnessappprj.network.RetrofitClient
import com.example.fitnessappprj.viewmodel.FitnessViewModel
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun DiabetesForm(
    modifier: Modifier = Modifier,
    navController: NavController,
    fitnessViewModel: FitnessViewModel
) {
    val fields = remember {
        mutableStateMapOf(
            "Pregnancies" to "",
            "Glucose" to "",
            "BloodPressure" to "",
            "SkinThickness" to "",
            "Insulin" to "",
            "BMI" to "",
            "DiabetesPedigreeFunction" to "",
            "Age" to ""
        )
    }

    val context = LocalContext.current
    val showDialog = remember { mutableStateOf(false) }
    val dialogMessage = remember { mutableStateOf("") }

    // Track loading state
    val isLoading = remember { mutableStateOf(false) }

    // Track if report was shown
    val reportShown = remember { mutableStateOf(false) }

    val diabetesPrediction = fitnessViewModel.diabetesResponse.observeAsState()

    // Handle prediction changes
    LaunchedEffect(diabetesPrediction.value) {
        val currentPrediction = diabetesPrediction.value

        // Check if we have a new result while in loading state
        if (isLoading.value && currentPrediction != null) {
            isLoading.value = false

            val report = if (currentPrediction.diabetes_prediction == 1) "Diabetes" else "No Diabetes"
            dialogMessage.value = "Your Diabetes Report: $report"
            showDialog.value = true
            reportShown.value = true
        }
    }

    Scaffold(
        topBar = { FitTopAppBar(navController = navController) },
    ) { paddingValues ->
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
                fields.keys.forEach { field ->
                    item {
                        OutlinedTextField(
                            value = fields[field] ?: "",
                            onValueChange = { fields[field] = it },
                            label = { Text(field, fontSize = 12.sp) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            textStyle = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            val requestBody = DiabetesRequest(
                                Pregnancies = fields["Pregnancies"]?.toIntOrNull() ?: 0,
                                Glucose = fields["Glucose"]?.toIntOrNull() ?: 0,
                                BloodPressure = fields["BloodPressure"]?.toIntOrNull() ?: 0,
                                SkinThickness = fields["SkinThickness"]?.toIntOrNull() ?: 0,
                                Insulin = fields["Insulin"]?.toIntOrNull() ?: 0,
                                BMI = fields["BMI"]?.toDoubleOrNull() ?: 0.0,
                                DiabetesPedigreeFunction = fields["DiabetesPedigreeFunction"]?.toDoubleOrNull() ?: 0.0,
                                Age = fields["Age"]?.toIntOrNull() ?: 0
                            )

                            // Set loading state to true before making the request
                            isLoading.value = true
                            reportShown.value = false

                            fitnessViewModel.predictDiabetes(requestBody)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.base))
                    ) {
                        if (isLoading.value) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Submit", fontSize = 18.sp)
                        }
                    }
                }
            }

            if (showDialog.value) {
                ShowPopupDialog(
                    message = dialogMessage.value,
                    dialogState = showDialog
                )
            }
        }
    }
}