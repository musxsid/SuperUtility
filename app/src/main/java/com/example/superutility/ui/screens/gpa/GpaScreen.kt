package com.example.superutility.ui.screens.gpa

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun GpaScreen(navController: NavController) {
    var credit by remember { mutableStateOf("") }
    var grade by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("Your SGPA will appear here") }

    Scaffold(topBar = { TopAppBar(title = { Text("GPA Calculator") }) }) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)
            .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "Calculate your SGPA easily!", style = MaterialTheme.typography.subtitle1)

            OutlinedTextField(value = credit, onValueChange = { credit = it }, label = { Text("Total Credits") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp))
            OutlinedTextField(value = grade, onValueChange = { grade = it }, label = { Text("Grade Points (0–10)") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp))

            Button(onClick = {
                val c = credit.toDoubleOrNull()
                val g = grade.toDoubleOrNull()
                result = if (c != null && g != null && c > 0) {
                    "Your SGPA is: %.2f".format(g)
                } else {
                    "Please enter valid values"
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Calculate")
            }

            Divider()

            Text(result, style = MaterialTheme.typography.h6)
        }
    }
}
