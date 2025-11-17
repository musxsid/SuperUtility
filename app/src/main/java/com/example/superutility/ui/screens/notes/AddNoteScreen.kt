package com.example.superutility.ui.screens.notes

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.superutility.SuperUtilityApp
import com.example.superutility.viewmodels.NotesViewModel
import com.example.superutility.viewmodels.NotesViewModelFactory

@Composable
fun AddNoteScreen(navController: NavController) {

    val app = LocalContext.current.applicationContext as SuperUtilityApp
    val viewModel: NotesViewModel = viewModel(factory = NotesViewModelFactory(app.notesRepository))

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Add Note") }) }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Content") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

            Spacer(Modifier.height(20.dp))

            Button(
                onClick = {
                    if (title.isNotEmpty() || content.isNotEmpty()) {
                        viewModel.addNote(title, content)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save Note")
            }
        }
    }
}
