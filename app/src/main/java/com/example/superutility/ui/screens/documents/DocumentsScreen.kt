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
    val documentFolders = listOf(
        "ID Proofs",
        "Certificates",
        "College Documents",
        "Bills & Receipts",
        "Personal Docs"
    )

    Scaffold(topBar = { TopAppBar(title = { Text("Documents") }) }) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            documentFolders.forEach { name ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        // later open folder or local storage picker
                    },
                    elevation = 4.dp
                ) {
                    Text(text = name, style = MaterialTheme.typography.subtitle1, modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}
