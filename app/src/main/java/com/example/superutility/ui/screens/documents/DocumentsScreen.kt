package com.example.superutility.ui.screens.documents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun DocumentsScreen(navController: NavController) {
    val folders = listOf("ID Proofs", "Certificates", "College Docs", "Bills", "Personal")
    Scaffold(topBar = { TopAppBar(title = { Text("Documents") }) }) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            folders.forEach { f ->
                Card(modifier = Modifier.fillMaxWidth().clickable { /* TODO: open folder UI */ }) {
                    Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(f, style = MaterialTheme.typography.subtitle1)
                        Text("Open", style = MaterialTheme.typography.caption)
                    }
                }
            }
        }
    }
}
