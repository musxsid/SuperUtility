package com.example.superutility.ui.screens.assignments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class AssignmentUI(val title: String, val subject: String, val dueInDays: Int)

@Composable
fun AssignmentsScreen(navController: NavController) {
    val assignments = remember { mutableStateListOf<AssignmentUI>() }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Assignments") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_assignment") }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        if (assignments.isEmpty()) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(padding), contentAlignment = Alignment.Center) {
                Text("No assignments yet. Tap + to add")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding).padding(12.dp)) {
                items(assignments) { a ->
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp), elevation = 4.dp) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(a.title, style = MaterialTheme.typography.subtitle1)
                                Text(a.subject, style = MaterialTheme.typography.body2)
                                Text("Due in ${a.dueInDays} days", style = MaterialTheme.typography.caption)
                            }
                            IconButton(onClick = { assignments.remove(a) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete")
                            }
                        }
                    }
                }
            }
        }
    }
}
