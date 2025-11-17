package com.example.superutility.ui.screens.assignments

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.util.*

@Composable
fun AddAssignmentScreen(navController: NavController) {
    var title by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var days by remember { mutableStateOf("") }
    var attachmentName by remember { mutableStateOf<String?>(null) }

    Scaffold(topBar = { TopAppBar(title = { Text("Add Assignment") }) }) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = subject, onValueChange = { subject = it }, label = { Text("Subject") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = days, onValueChange = { days = it }, label = { Text("Due in (days)") }, modifier = Modifier.fillMaxWidth())

            // simple fake attach button (UI-only)
            Button(onClick = { attachmentName = "example_attachment.pdf" }, modifier = Modifier.fillMaxWidth()) {
                Text(if (attachmentName == null) "Attach file (demo)" else "Attached: $attachmentName")
            }

            Spacer(Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = { navController.popBackStack() }, modifier = Modifier.weight(1f)) {
                    Text("Cancel")
                }
                Button(onClick = {
                    // For UI-only demo, we just navigate back.
                    // In real app: save to DB
                    navController.popBackStack()
                }, modifier = Modifier.weight(1f)) {
                    Text("Save")
                }
            }
        }
    }
}
