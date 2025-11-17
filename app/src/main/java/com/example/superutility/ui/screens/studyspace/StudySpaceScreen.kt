package com.example.superutility.ui.screens.studyspace

import androidx.compose.foundation.layout.*
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
    var running by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Scaffold(topBar = { TopAppBar(title = { Text("Study Space") }) }) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("Pomodoro Timer", style = MaterialTheme.typography.h6)
            Text(String.format("%02d:%02d", minutes, seconds), style = MaterialTheme.typography.h4)

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = {
                    if (!running) {
                        running = true
                        scope.launch {
                            while (running && (minutes > 0 || seconds > 0)) {
                                delay(1000)
                                if (seconds == 0) {
                                    if (minutes > 0) {
                                        minutes--
                                        seconds = 59
                                    }
                                } else seconds--
                            }
                            running = false
                        }
                    }
                }) { Text("Start") }
                Button(onClick = { running = false }) { Text("Pause") }
                Button(onClick = { running = false; minutes = 25; seconds = 0 }) { Text("Reset") }
            }

            Divider()

            Text("Quick Notes", style = MaterialTheme.typography.subtitle1)
            var quick by remember { mutableStateOf("") }
            OutlinedTextField(value = quick, onValueChange = { quick = it }, modifier = Modifier.fillMaxWidth(), label = { Text("Quick note (not saved)") })
            Row(Modifier.fillMaxWidth()) {
                Button(onClick = { quick = "" }, modifier = Modifier.weight(1f)) { Text("Clear") }
                Spacer(Modifier.width(8.dp))
                Button(onClick = { /* TODO: Push to notes screen in future */ }, modifier = Modifier.weight(1f)) { Text("Send to Notes") }
            }
        }
    }
}
