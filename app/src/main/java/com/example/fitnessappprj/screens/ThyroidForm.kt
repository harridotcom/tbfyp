package com.example.fitnessapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fitnessapp.R
import com.example.fitnessapp.misc.FitTopAppBar
import com.example.fitnessapp.misc.ShowPopupDialog
import com.example.fitnessappprj.network.ThyroidRequest
import com.example.fitnessappprj.viewmodel.FitnessViewModel
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun ThyroidForm(
    modifier: Modifier = Modifier,
    navController: NavController,
    fitnessViewModel: FitnessViewModel
) {
    val fields = remember {
        mutableStateMapOf(
            "Age" to "", "Sex" to "", "On Thyroxine" to "", "Query on Thyroxine" to "",
            "On Antithyroid Medication" to "", "Sick" to "", "Pregnant" to "", "Thyroid Surgery" to "",
            "I131 Treatment" to "", "Query Hypothyroid" to "", "Query Hyperthyroid" to "", "Lithium" to "",
            "Goitre" to "", "Tumor" to "", "Hypopituitary" to "", "Psych" to "", "TSH Measured" to "",
            "TSH" to "", "T3 Measured" to "", "T3" to "", "TT4 Measured" to "", "TT4" to "",
            "T4U Measured" to "", "T4U" to "", "FTI Measured" to "", "FTI" to "", "TBG Measured" to "",
            "TBG" to "", "Referral Source" to ""
        )
    }

    // Track loading state
    val isLoading = remember { mutableStateOf(false) }

    // Show dialog state and message
    val showDialog = remember { mutableStateOf(false) }
    val dialogMessage = remember { mutableStateOf("") }

    // Observe thyroid prediction response
    val thyroidPrediction = fitnessViewModel.thyroidResponse.observeAsState()

    // Handle prediction changes
    LaunchedEffect(thyroidPrediction.value) {
        val currentPrediction = thyroidPrediction.value

        // Check if we have a new result while in loading state
        if (isLoading.value && currentPrediction != null) {
            isLoading.value = false

            val resultMessage = if (currentPrediction.thyroid_prediction == 1) {
                "Yes, you've been diagnosed with Thyroid. Please consult with a healthcare provider."
            } else {
                "No thyroid condition detected based on the provided information."
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

            // LazyColumn to display form fields
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Iterate over fields and display each one
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

                // Submit button with loading indicator
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            try {
                                // Create request body with form data
                                val requestBody = ThyroidRequest(
                                    age = fields["Age"]?.toIntOrNull() ?: 0,
                                    sex = fields["Sex"] ?: "",
                                    on_thyroxine = fields["On Thyroxine"] ?: "",
                                    query_on_thyroxine = fields["Query on Thyroxine"] ?: "",
                                    on_antithyroid_medication = fields["On Antithyroid Medication"] ?: "",
                                    sick = fields["Sick"] ?: "",
                                    pregnant = fields["Pregnant"] ?: "",
                                    thyroid_surgery = fields["Thyroid Surgery"] ?: "",
                                    I131_treatment = fields["I131 Treatment"] ?: "",
                                    query_hypothyroid = fields["Query Hypothyroid"] ?: "",
                                    query_hyperthyroid = fields["Query Hyperthyroid"] ?: "",
                                    lithium = fields["Lithium"] ?: "",
                                    goitre = fields["Goitre"] ?: "",
                                    tumor = fields["Tumor"] ?: "",
                                    hypopituitary = fields["Hypopituitary"] ?: "",
                                    psych = fields["Psych"] ?: "",
                                    TSH_measured = fields["TSH Measured"] ?: "",
                                    TSH = fields["TSH"]?.toDoubleOrNull() ?: 0.0,
                                    T3_measured = fields["T3 Measured"] ?: "",
                                    T3 = fields["T3"]?.toDoubleOrNull() ?: 0.0,
                                    TT4_measured = fields["TT4 Measured"] ?: "",
                                    TT4 = fields["TT4"]?.toIntOrNull() ?: 0,
                                    T4U_measured = fields["T4U Measured"] ?: "",
                                    T4U = fields["T4U"]?.toDoubleOrNull() ?: 0.0,
                                    FTI_measured = fields["FTI Measured"] ?: "",
                                    FTI = fields["FTI"]?.toIntOrNull() ?: 0,
                                    TBG_measured = fields["TBG Measured"] ?: "",
                                    TBG = fields["TBG"] ?: "",
                                    referral_source = fields["Referral Source"] ?: ""
                                )

                                // Set loading state to true before making the request
                                isLoading.value = true

                                // Make the prediction request
                                fitnessViewModel.predictThyroid(requestBody)
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