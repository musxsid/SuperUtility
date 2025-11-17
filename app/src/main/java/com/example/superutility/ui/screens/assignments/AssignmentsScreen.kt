package com.example.superutility.ui.screens.assignments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.superutility.SuperUtilityApp
import com.example.superutility.data.room.entities.AssignmentEntity
import com.example.superutility.viewmodels.AssignmentViewModel
import com.example.superutility.viewmodels.AssignmentViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AssignmentsScreen(navController: NavController) {

    val context = LocalContext.current
    val app = context.applicationContext as SuperUtilityApp

    val viewModel: AssignmentViewModel = viewModel(
        factory = AssignmentViewModelFactory(app.assignmentRepository)
    )

    val assignments by viewModel.assignments.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Assignments") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_assignment") }) {
                Text("+")
            }
        }
    ) { padding ->

        if (assignments.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No assignments yet.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            ) {
                items(assignments) { assignment ->
                    AssignmentCard(
                        assignment = assignment,
                        onDelete = { viewModel.deleteAssignment(assignment) },
                        onEdit = { navController.navigate("edit_assignment/${assignment.id}") }
                    )
                }
            }
        }
    }
}

@Composable
fun AssignmentCard(
    assignment: AssignmentEntity,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onEdit() },
        elevation = 6.dp
    ) {
        Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.weight(1f)) {
                Text(assignment.title, style = MaterialTheme.typography.h6)
                assignment.description?.let { Text(it, style = MaterialTheme.typography.body2) }
                val formatted = remember(assignment.timestamp) {
                    SimpleDateFormat("dd MMM yyyy • hh:mm a", Locale.getDefault()).format(Date(assignment.timestamp))
                }
                Text("Added: $formatted", style = MaterialTheme.typography.caption)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}
