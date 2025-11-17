package com.example.superutility.ui.screens.assignments

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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

@Composable
fun EditAssignmentScreen(navController: NavController, id: Int) {

    val context = LocalContext.current
    val app = context.applicationContext as SuperUtilityApp

    val viewModel: AssignmentViewModel = viewModel(
        factory = AssignmentViewModelFactory(app.assignmentRepository)
    )

    var assignment by remember { mutableStateOf<AssignmentEntity?>(null) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(id) {
        assignment = viewModel.getAssignment(id)
        loading = false
    }

    if (loading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
        return
    }

    if (assignment == null) {
        Text("Assignment not found")
        return
    }

    var title by remember { mutableStateOf(assignment!!.title) }
    var description by remember { mutableStateOf(assignment!!.description ?: "") }

    Scaffold(topBar = { TopAppBar(title = { Text("Edit Assignment") }) }) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(12.dp))
            OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(20.dp))
            Button(onClick = {
                viewModel.updateAssignment(assignment!!.copy(title = title, description = description))
                navController.popBackStack()
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Save Changes")
            }
        }
    }
}
