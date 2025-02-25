package com.example.fitnessapp.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fitnessapp.R

private val CardColor = Color(0xFFFFFFFF)
private val TextColor = Color(0xFF4A4A4A)
private val BorderColor = Color(0xFFB0B0B0)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstAid(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Emergency Guide", fontSize = 20.sp) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = colorResource(R.color.base))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.backgroundColor))
                .padding(16.dp)
                .padding(paddingValues)
        ) {
            ExpandableSection(title = "Diabetes-Related Emergencies", content = {
                SectionText("a) Hypoglycemia (Low Blood Sugar - Below 70 mg/dL)\nSymptoms: Sweating, dizziness, shakiness, confusion, weakness, blurred vision, rapid heartbeat.\nFirst Aid: Give 15g of fast-acting carbohydrates, recheck blood sugar in 15 min, and call emergency services if needed.")
                SectionText(" b) Hyperglycemia (High Blood Sugar - Above 250 mg/dL)\nSymptoms Excessive thirst, frequent urination, fatigue, nausea, fruity breath odor.\nFirst Aid Encourage water intake, check blood sugar, administer insulin, and seek medical help if necessary.")
                SectionText(" c) Diabetic Ketoacidosis (DKA) - Life-Threatening\n" +
                        " **Symptoms:** Vomiting, deep labored breathing, confusion, fruity-smelling breath.\n" +
                        " **First Aid:**- Call emergency services immediately.- Encourage fluid intake if conscious.- Check for ketones in urine if possible")
            })

            ExpandableSection(title = "Thyroid-Related Emergencies", content = {
                SectionText("a) Myxedema Coma (Severe Hypothyroidism)\nSymptoms: Extreme fatigue, confusion, low heart rate, difficulty breathing.\n**First Aid Call emergency services immediately, keep the person warm, and avoid giving food or drink if unconscious.")
                SectionText("b) Thyroid Storm (Severe Hyperthyroidism)\nSymptoms: High fever, rapid heartbeat, agitation, confusion.\nFirst Aid: Call emergency services immediately, keep the person cool, and avoid stimulants.")
            })

            ExpandableSection(title = "Obesity-Related Emergencies", content = {
                SectionText("a) Heart Attack\nSymptoms Chest pain, shortness of breath, nausea, cold sweats, dizziness.\n**First Aid Call emergency services, have the person sit down, and give aspirin if conscious.")
                SectionText("b) Stroke\nSymptoms Face drooping, arm weakness, speech difficulty.\n**First Aid: Use the FAST method and call emergency services immediately.")
                SectionText("c) Respiratory Distress / Sleep Apnea Attack\n" +
                        " Symptoms: Difficulty breathing, loud snoring, choking sensation during sleep.\n" +
                        " First Aid:\n" +
                        "- Keep the person upright and ensure an open airway.- If using a CPAP machine, ensure it is properly working.- Call emergency services if breathing does not improve")
            })
        }
    }
}

@Composable
fun ExpandableSection(title: String, content: @Composable () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(CardColor, shape = RoundedCornerShape(16.dp))
                .border(1.dp, BorderColor, RoundedCornerShape(16.dp))
                .clickable { expanded = !expanded }
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, fontSize = 18.sp, color = TextColor, modifier = Modifier.weight(1f))
            Icon(
                imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = TextColor
            )
        }
        if (expanded) {
            Column(modifier = Modifier.padding(8.dp)) { content() }
        }
    }
}

@Composable
fun SectionText(text: String) {
    Text(text = text, color = TextColor, fontSize = 14.sp, modifier = Modifier.padding(8.dp))
}
