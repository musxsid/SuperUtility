package com.example.superutility.ui.screens.assignments

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AddAssignmentScreen(navController: NavController) {
    var title by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var days by remember { mutableStateOf("") }

    Scaffold(topBar = { TopAppBar(title = { Text("Add Assignment") }) }) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = subject, onValueChange = { subject = it }, label = { Text("Subject") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = days, onValueChange = { days = it }, label = { Text("Due in days") }, modifier = Modifier.fillMaxWidth())

            Button(onClick = {
                // For UI-only version: just go back
                navController.popBackStack()
            }, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.Check, contentDescription = "Save")
                Spacer(Modifier.width(8.dp))
                Text("Save")
            }
        }
    }
}
