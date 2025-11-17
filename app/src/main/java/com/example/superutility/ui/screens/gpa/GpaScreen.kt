package com.example.superutility.ui.screens.gpa

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun GpaScreen(navController: NavController) {
    var credits by remember { mutableStateOf("") }
    var grade by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("Your SGPA will appear here") }

    Scaffold(topBar = { TopAppBar(title = { Text("GPA Calculator") }) }) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(value = credits, onValueChange = { credits = it }, label = { Text("Total Credits") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = grade, onValueChange = { grade = it }, label = { Text("Grade Points (0–10)") }, modifier = Modifier.fillMaxWidth())

            Button(onClick = {
                val c = credits.toDoubleOrNull()
                val g = grade.toDoubleOrNull()
                if (c != null && g != null && c > 0) {
                    result = "SGPA: %.2f".format(g)
                } else result = "Please enter valid numbers"
            }, modifier = Modifier.fillMaxWidth()) { Text("Calculate") }

            Text(result, style = MaterialTheme.typography.h6)
        }
    }
}
