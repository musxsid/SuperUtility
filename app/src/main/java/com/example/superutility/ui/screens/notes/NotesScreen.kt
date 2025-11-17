package com.example.superutility.ui.screens.notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.superutility.SuperUtilityApp
import com.example.superutility.data.room.entities.NoteEntity
import com.example.superutility.viewmodels.NotesViewModel
import com.example.superutility.viewmodels.NotesViewModelFactory
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun NotesScreen(navController: NavController) {

    val context = LocalContext.current
    val app = context.applicationContext as SuperUtilityApp

    val viewModel: NotesViewModel = viewModel(
        factory = NotesViewModelFactory(app.notesRepository)
    )

    val notes by viewModel.notes.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Notes") }) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_note") }
            ) {
                Text("+")
            }
        }
    ) { padding ->

        if (notes.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No notes yet.")
            }

        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {
                items(notes) { note ->
                    NoteCard(
                        note = note,
                        onEdit = { navController.navigate("edit_note/${note.id}") },
                        onDelete = { viewModel.deleteNote(note) }
                    )
                }
            }
        }
    }
}

@Composable
fun NoteCard(
    note: NoteEntity,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        elevation = 6.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onEdit() }
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(note.title, style = MaterialTheme.typography.h6)
            Spacer(Modifier.height(6.dp))
            Text(note.content, style = MaterialTheme.typography.body2)

            Spacer(Modifier.height(6.dp))

            Text(
                text = "Created: ${
                    SimpleDateFormat("dd MMM yyyy • hh:mm a", Locale.getDefault())
                        .format(note.timestamp)
                }",
                style = MaterialTheme.typography.caption
            )

            Spacer(Modifier.height(10.dp))

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {

                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit Note")
                }

                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Note")
                }
            }
        }
    }
}
