package com.example.superutility.ui.screens.documents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.superutility.SuperUtilityApp
import com.example.superutility.data.room.entities.DocumentEntity
import com.example.superutility.viewmodels.DocumentsViewModel
import com.example.superutility.viewmodels.DocumentsViewModelFactory
import androidx.compose.ui.platform.LocalContext
import java.io.File

@Composable
fun DocumentsScreen(
    navController: NavController,
    viewModel: DocumentsViewModel = viewModel(
        factory = DocumentsViewModelFactory(
            (LocalContext.current.applicationContext as SuperUtilityApp).documentsRepository
        )
    )
) {
    val docs by viewModel.allDocuments.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Documents") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_document") }) {
                Text("+")
            }
        }
    ) { padding ->
        if (docs.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text("No documents yet.")
            }
        } else {
            LazyColumn(modifier = Modifier.padding(padding).padding(12.dp)) {
                items(docs) { doc ->
                    DocumentCard(doc, onOpen = {
                        // try to open with default intent (not handled here) — or show path
                    })
                }
            }
        }
    }
}

@Composable
fun DocumentCard(doc: DocumentEntity, onOpen: () -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(6.dp)
        .clickable { onOpen() }, elevation = 6.dp) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(doc.fileName, style = MaterialTheme.typography.subtitle1)
            Spacer(Modifier.height(6.dp))
            Text(doc.folderName ?: "Root", style = MaterialTheme.typography.caption)
            Spacer(Modifier.height(4.dp))
            Text("Saved at: ${File(doc.filePath).name}", style = MaterialTheme.typography.caption)
        }
    }
}
