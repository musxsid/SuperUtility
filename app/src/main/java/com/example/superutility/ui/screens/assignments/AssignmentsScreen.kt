package com.example.superutility.ui.screens.assignments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.superutility.models.AssignmentUI
import java.util.*

@Composable
fun AssignmentsScreen(navController: NavController) {
    // in-memory list for demo
    val assignments = remember { mutableStateListOf<AssignmentUI>() }

    // sample seed
    if (assignments.isEmpty()) {
        assignments.add(
            AssignmentUI(
                id = UUID.randomUUID().toString(),
                title = "Build UI for SuperUtility",
                subject = "Mobile",
                dueInDays = 3,
                attachedName = null
            )
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Assignments") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_assignment") }) {
                Text("+")
            }
        }
    ) { padding ->
        if (assignments.isEmpty()) {
            Box(modifier = Modifier
                .padding(padding)
                .fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("No assignments yet. Tap + to add.")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding).padding(16.dp)) {
                items(assignments) { a ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(a.title, style = MaterialTheme.typography.subtitle1)
                            Spacer(Modifier.height(6.dp))
                            Text("${a.subject} • Due in ${a.dueInDays} days", style = MaterialTheme.typography.body2)
                            a.attachedName?.let {
                                Spacer(Modifier.height(6.dp))
                                Text("Attachment: $it", style = MaterialTheme.typography.caption)
                            }
                            Spacer(Modifier.height(8.dp))
                            Row {
                                TextButton(onClick = {
                                    // TODO later: Edit assignment
                                }) { Text("Edit") }
                                Spacer(Modifier.width(8.dp))
                                TextButton(onClick = { assignments.remove(a) }) { Text("Delete") }
                            }
                        }
                    }
                }
            }
        }
    }
}
