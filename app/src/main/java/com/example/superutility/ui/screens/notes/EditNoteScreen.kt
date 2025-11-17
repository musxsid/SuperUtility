package com.example.superutility.ui.screens.notes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.superutility.SuperUtilityApp
import com.example.superutility.data.room.entities.NoteEntity
import com.example.superutility.viewmodels.NotesViewModel
import com.example.superutility.viewmodels.NotesViewModelFactory


@Composable
fun EditNoteScreen(
    navController: NavController,
    id: Int
) {
    val context = LocalContext.current
    val app = context.applicationContext as SuperUtilityApp

    val viewModel: NotesViewModel = viewModel(
        factory = NotesViewModelFactory(app.notesRepository)
    )

    var note by remember { mutableStateOf<NoteEntity?>(null) }
    var loading by remember { mutableStateOf(true) }

    // Load note
    LaunchedEffect(id) {
        note = viewModel.getNote(id)
        loading = false
    }

    if (loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) { CircularProgressIndicator() }
        return
    }

    if (note == null) {
        Text("Error loading note.")
        return
    }

    var text by remember { mutableStateOf(note!!.content) }
    var fontSize by remember { mutableStateOf(16f) }
    var bold by remember { mutableStateOf(false) }
    var italic by remember { mutableStateOf(false) }

    Column(Modifier.padding(16.dp)) {

        Text("Edit Note", style = MaterialTheme.typography.h5)
        Spacer(Modifier.height(16.dp))

        // Formatting controls
        Row(verticalAlignment = Alignment.CenterVertically) {

            // Bold Button
            TextButton(onClick = { bold = !bold }) {
                Text("B", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.width(10.dp))

            // Italic Button
            TextButton(onClick = { italic = !italic }) {
                Text("I", fontSize = 22.sp, fontStyle = FontStyle.Italic)
            }
            Spacer(Modifier.width(10.dp))

            // Font size slider
            Slider(
                value = fontSize,
                onValueChange = { fontSize = it },
                valueRange = 12f..28f,
                modifier = Modifier.weight(1f)
            )
        }

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            textStyle = TextStyle(
                fontSize = fontSize.sp,
                fontWeight = if (bold) FontWeight.Bold else FontWeight.Normal,
                fontStyle = if (italic) FontStyle.Italic else FontStyle.Normal
            ),
            label = { Text("Note Content") }
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.updateNote(
                    note!!.copy(content = text)
                )
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Changes")
        }
    }
}
