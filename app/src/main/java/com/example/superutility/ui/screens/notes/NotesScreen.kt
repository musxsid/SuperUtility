package com.example.superutility.ui.screens.notes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.NoteAdd
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class NoteUI(val text: String)

@Composable
fun NotesScreen(navController: NavController) {
    var input by remember { mutableStateOf("") }
    val notes = remember { mutableStateListOf<NoteUI>() }

    Scaffold(topBar = { TopAppBar(title = { Text("Notes") }) }) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {

            OutlinedTextField(value = input, onValueChange = { input = it }, label = { Text("Write a note") }, modifier = Modifier.fillMaxWidth())

            Button(onClick = {
                if (input.isNotBlank()) {
                    notes.add(NoteUI(input))
                    input = ""
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Icon(Icons.Default.NoteAdd, contentDescription = "Add")
                Spacer(Modifier.width(8.dp))
                Text("Add Note")
            }

            Divider()

            if (notes.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No notes yet")
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(notes) { n ->
                        Card(modifier = Modifier.fillMaxWidth(), elevation = 4.dp) {
                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                Text(n.text, modifier = Modifier.weight(1f))
                                IconButton(onClick = { notes.remove(n) }) {
                                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
