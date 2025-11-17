package com.example.superutility.ui.screens.studyspace

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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

    val durationOptions = listOf(10, 15, 20, 25, 30, 45, 60)

    var selectedMinutes by remember { mutableStateOf(25) }

    var minutes by remember { mutableStateOf(selectedMinutes) }
    var seconds by remember { mutableStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Study Space") }) }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // TITLE
            Text(
                text = "Pomodoro Timer",
                style = MaterialTheme.typography.h5
            )

            // DROPDOWN SELECTOR
            DurationDropdown(
                selectedMinutes = selectedMinutes,
                onSelect = {
                    selectedMinutes = it
                    if (!isRunning) {
                        minutes = it
                        seconds = 0
                    }
                }
            )

            // TIMER DISPLAY
            Text(
                text = String.format("%02d:%02d", minutes, seconds),
                style = MaterialTheme.typography.h3,
                textAlign = TextAlign.Center
            )

            // CONTROLS (START / PAUSE / RESET)
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {

                Button(
                    onClick = {
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

                                if (minutes == 0 && seconds == 0) {
                                    // TODO: save session later
                                }

                                isRunning = false
                            }
                        }
                    }
                ) { Text("Start") }

                Button(onClick = { isRunning = false }) {
                    Text("Pause")
                }

                Button(onClick = {
                    isRunning = false
                    minutes = selectedMinutes
                    seconds = 0
                }) {
                    Text("Reset")
                }
            }

            Divider()

            // QUICK NOTES (simple text for now)
            Text("Quick Notes", style = MaterialTheme.typography.h6)

            var notes by remember { mutableStateOf("") }

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Write something…") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                shape = RoundedCornerShape(12.dp)
            )

            Button(
                onClick = { notes = "" },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Clear Notes")
            }
        }
    }
}

@Composable
fun DurationDropdown(
    selectedMinutes: Int,
    onSelect: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        Button(
            onClick = { expanded = true },
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Duration: $selectedMinutes min")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            listOf(10, 15, 20, 25, 30, 45, 60).forEach { value ->
                DropdownMenuItem(onClick = {
                    onSelect(value)
                    expanded = false
                }) {
                    Text("$value minutes")
                }
            }
        }
    }
}
