package com.example.superutility.ui.screens.assignments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlin.random.Random

@Composable
fun AssignmentsScreen(navController: NavController) {
    // simple in-memory list (replace with Room later)
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
                .padding(padding)
                .fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No assignments yet.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {
                items(assignments) { item ->
                    AssignmentCard(
                        item = item,
                        onDelete = { assignments.remove(item) }
                    )
                }
            }
        }
    }
}

@Composable
fun AssignmentCard(item: AssignmentUI, onDelete: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(item.title, style = MaterialTheme.typography.subtitle1)
                Text(item.subject, style = MaterialTheme.typography.body2)
                Text("Due in ${item.dueInDays} days", style = MaterialTheme.typography.caption)
            }

            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}
