package com.example.superutility.ui.screens.gpa

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun GpaScreen(navController: NavController) {
    var credits by remember { mutableStateOf("") }
    var grade by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("SGPA shows here") }

    Scaffold(topBar = { TopAppBar(title = { Text("GPA Calculator") }) }) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)) {

            OutlinedTextField(value = credits, onValueChange = { credits = it }, label = { Text("Total Credits") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = grade, onValueChange = { grade = it }, label = { Text("Grade Points (0-10)") }, modifier = Modifier.fillMaxWidth())

            Button(onClick = {
                val c = credits.toDoubleOrNull()
                val g = grade.toDoubleOrNull()
                result = if (c != null && g != null && c > 0) {
                    "SGPA: %.2f".format(g)
                } else {
                    "Please enter valid values"
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.Calculate, contentDescription = "Calc")
                Spacer(Modifier.width(8.dp))
                Text("Calculate")
            }

            Divider()

            Text(result, style = MaterialTheme.typography.h6)
        }
    }
}
