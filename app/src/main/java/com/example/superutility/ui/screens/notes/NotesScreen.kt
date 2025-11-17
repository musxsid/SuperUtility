package com.example.superutility.ui.screens.notes

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.superutility.models.NoteUI
import java.util.*

@Composable
fun NotesScreen(navController: NavController) {
    val notes = remember { mutableStateListOf<NoteUI>() }
    var text by remember { mutableStateOf("") }

    Scaffold(topBar = { TopAppBar(title = { Text("Notes") }) }) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(value = text, onValueChange = { text = it }, label = { Text("Write note") }, modifier = Modifier.fillMaxWidth())
            Button(onClick = {
                if (text.isNotBlank()) {
                    notes.add(NoteUI(UUID.randomUUID().toString(), text, System.currentTimeMillis()))
                    text = ""
                }
            }, modifier = Modifier.fillMaxWidth()) { Text("Add Note") }

            if (notes.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                    Text("No notes yet.")
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(notes) { n ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(n.content, style = MaterialTheme.typography.body1)
                                Spacer(Modifier.height(6.dp))
                                Row {
                                    TextButton(onClick = {}) { Text("Edit") }
                                    Spacer(Modifier.width(8.dp))
                                    TextButton(onClick = { notes.remove(n) }) { Text("Delete") }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
