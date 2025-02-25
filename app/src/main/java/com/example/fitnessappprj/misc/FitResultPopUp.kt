package com.example.fitnessapp.misc

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import com.example.fitnessapp.R

@Composable
fun ShowPopupDialog(
    message: String,
    dialogState: MutableState<Boolean>
) {
    // Check if the dialog should be shown
    if (dialogState.value) {
        // Show the AlertDialog
        AlertDialog(
            onDismissRequest = {
                dialogState.value = false // Close dialog when dismissed
            },
            title = {
                Text(text = "Result", fontSize = 20.sp) // Title of the popup
            },
            text = {
                Text(text = message, fontSize = 16.sp) // Display the message
            },
            confirmButton = {
                Button(
                    onClick = { dialogState.value = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.base)
                    )
                ) {
                    Text("Close")
                }
            }
        )
    }
}