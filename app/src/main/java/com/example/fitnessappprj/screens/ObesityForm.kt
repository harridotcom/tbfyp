package com.example.fitnessapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitnessapp.R
import com.example.fitnessapp.misc.FitTopAppBar
import com.example.fitnessapp.misc.ShowPopupDialog
import com.example.fitnessappprj.network.ObesityRequest
import com.example.fitnessappprj.viewmodel.FitnessViewModel
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun ObesityForm(modifier: Modifier = Modifier, navController: NavController, fitnessViewModel: FitnessViewModel) {
    val fields = remember {
        mutableStateMapOf(
            "Gender" to "",
            "Age" to "",
            "Height" to "",
            "Weight" to "",
            "family_history_with_overweight" to "",
            "FAVC" to "",
            "FCVC" to "",
            "NCP" to "",
            "CAEC" to "",
            "SMOKE" to "",
            "CH2O" to "",
            "SCC" to "",
            "FAF" to "",
            "TUE" to "",
            "CALC" to "",
            "MTRANS" to ""
        )
    }

    // Track loading state
    val isLoading = remember { mutableStateOf(false) }

    // Show dialog state and message
    val showDialog = remember { mutableStateOf(false) }
    val dialogMessage = remember { mutableStateOf("") }

    // Observe obesity prediction response
    val obesityPrediction = fitnessViewModel.obesityResponse.observeAsState()

    // Handle prediction changes
    LaunchedEffect(obesityPrediction.value) {
        val currentPrediction = obesityPrediction.value

        // Check if we have a new result while in loading state
        if (isLoading.value && currentPrediction != null) {
            isLoading.value = false

            // Determine message based on obesity prediction
            val resultMessage = if (currentPrediction.obesity_prediction == 1) {
                "You are classified as obese. It's important to take action and consult a healthcare professional."
            } else {
                "You are not classified as obese based on the provided information."
            }

            dialogMessage.value = resultMessage
            showDialog.value = true
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
            // Display the dialog if showDialog is true
            if (showDialog.value) {
                ShowPopupDialog(
                    message = dialogMessage.value,
                    dialogState = showDialog
                )
            }

            // LazyColumn for the form fields
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
                            textStyle = MaterialTheme.typography.bodyMedium,
                            keyboardOptions = when (field) {
                                "Age", "Weight", "FCVC", "NCP", "CH2O", "FAF", "TUE" ->
                                    KeyboardOptions(keyboardType = KeyboardType.Number)
                                "Height" ->
                                    KeyboardOptions(keyboardType = KeyboardType.Decimal)
                                else -> KeyboardOptions.Default
                            }
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            try {
                                // Create request body with form data
                                val requestBody = ObesityRequest(
                                    Gender = fields["Gender"] ?: "",
                                    Age = fields["Age"]?.toIntOrNull() ?: 0,
                                    Height = fields["Height"]?.toDoubleOrNull() ?: 0.0,
                                    Weight = fields["Weight"]?.toIntOrNull() ?: 0,
                                    family_history_with_overweight = fields["family_history_with_overweight"] ?: "",
                                    FAVC = fields["FAVC"] ?: "",
                                    FCVC = fields["FCVC"]?.toIntOrNull() ?: 0,
                                    NCP = fields["NCP"]?.toIntOrNull() ?: 0,
                                    CAEC = fields["CAEC"] ?: "",
                                    SMOKE = fields["SMOKE"] ?: "",
                                    CH2O = fields["CH2O"]?.toIntOrNull() ?: 0,
                                    SCC = fields["SCC"] ?: "",
                                    FAF = fields["FAF"]?.toIntOrNull() ?: 0,
                                    TUE = fields["TUE"]?.toIntOrNull() ?: 0,
                                    CALC = fields["CALC"] ?: "",
                                    MTRANS = fields["MTRANS"] ?: ""
                                )

                                // Set loading state to true before making the request
                                isLoading.value = true

                                // Make the prediction request
                                fitnessViewModel.predictObesity(requestBody)
                            } catch (e: Exception) {
                                // Handle any parsing errors
                                dialogMessage.value = "Error in form data: ${e.message}"
                                showDialog.value = true
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.base)
                        ),
                        enabled = !isLoading.value
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
        }
    }
}