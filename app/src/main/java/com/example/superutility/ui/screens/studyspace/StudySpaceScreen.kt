package com.example.superutility.ui.screens.studyspace

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun StudySpaceScreen(navController: NavController) {
    var minutes by remember { mutableStateOf(25) }
    var seconds by remember { mutableStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }
    var notesText by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Scaffold(topBar = { TopAppBar(title = { Text("Study Space") }) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Pomodoro Timer", style = MaterialTheme.typography.h6)

            Text(String.format("%02d:%02d", minutes, seconds), style = MaterialTheme.typography.h4)

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = {
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
                }) { Text("Start") }

                Button(onClick = { isRunning = false }) { Text("Pause") }

                Button(onClick = {
                    isRunning = false
                    minutes = 25
                    seconds = 0
                }) { Text("Reset") }
            }

            Divider()

            Text("Quick Notes (not saved)", style = MaterialTheme.typography.subtitle1)
            OutlinedTextField(
                value = notesText,
                onValueChange = { notesText = it },
                label = { Text("Write something…") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(8.dp)
            )

            Button(onClick = { notesText = "" }, modifier = Modifier.fillMaxWidth()) {
                Text("Clear Notes")
            }
        }
    }
}
