package com.example.superutility.ui.screens.studyspace

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Stop
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun StudySpaceScreen(navController: NavController) {
    var minutes by remember { mutableStateOf(25) }
    var seconds by remember { mutableStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }
    var notes by remember { mutableStateOf("") }
    var dnd by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Scaffold(topBar = { TopAppBar(title = { Text("Study Space") }) }) { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {

            Text("Pomodoro Timer", style = MaterialTheme.typography.h6)

            Text(text = String.format("%02d:%02d", minutes, seconds), style = MaterialTheme.typography.h4, textAlign = TextAlign.Center)

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                IconButton(onClick = {
                    if (!isRunning) {
                        isRunning = true
                        scope.launch {
                            while (isRunning && (minutes > 0 || seconds > 0)) {
                                delay(1000)
                                if (seconds == 0) {
                                    if (minutes > 0) {
                                        minutes--
                                        seconds = 59
                                    }
                                } else {
                                    seconds--
                                }
                            }
                            isRunning = false
                        }
                    }
                }) { Icon(Icons.Default.PlayArrow, contentDescription = "Start") }

                IconButton(onClick = { isRunning = false }) { Icon(Icons.Default.Stop, contentDescription = "Pause") }

                IconButton(onClick = {
                    isRunning = false
                    minutes = 25; seconds = 0
                }) { Icon(Icons.Default.Refresh, contentDescription = "Reset") }
            }

            Divider()

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("In-app DND")
                Spacer(Modifier.width(12.dp))
                Switch(checked = dnd, onCheckedChange = { dnd = it })
            }

            OutlinedTextField(value = notes, onValueChange = { notes = it }, label = { Text("Quick notes (not saved)") }, modifier = Modifier.fillMaxWidth().height(120.dp))

            Button(onClick = { notes = "" }, modifier = Modifier.fillMaxWidth()) { Text("Clear Notes") }
        }
    }
}
