package com.example.superutility.ui.screens.documents

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.superutility.SuperUtilityApp
import com.example.superutility.data.room.entities.DocumentEntity
import com.example.superutility.viewmodels.*

@Composable
fun DocumentsScreen(navController: NavController) {

    val context = LocalContext.current
    val app = context.applicationContext as SuperUtilityApp
    val viewModel: DocumentsViewModel = viewModel(
        factory = DocumentsViewModelFactory(app.documentsRepository)
    )

    val documents by viewModel.allDocuments.collectAsState()
    val folders by viewModel.allFolders.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Documents") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_document") }) {
                Text("+")
            }
        }
    ) { padding ->

        Column(Modifier.padding(padding).padding(16.dp)) {

            // Folders
            if (folders.isNotEmpty()) {
                Text("Folders", style = MaterialTheme.typography.h6)
                Spacer(Modifier.height(8.dp))

                LazyColumn {
                    items(folders) { folder ->
                        FolderItem(
                            folder = folder ?: "Uncategorized",
                            onClick = { navController.navigate("folder/${folder ?: "Uncategorized"}") }
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))
            }

            Text("All Documents", style = MaterialTheme.typography.h6)
            Spacer(Modifier.height(8.dp))

            if (documents.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No documents yet.")
                }
            } else {
                LazyColumn {
                    items(documents) { doc ->
                        DocumentItem(
                            document = doc,
                            onOpen = {
                                val encodedPath = Uri.encode(doc.filePath)
                                val encodedMime = Uri.encode(doc.mimeType ?: "*/*")
                                navController.navigate("preview/$encodedPath/$encodedMime")
                            },
                            onDelete = { viewModel.deleteDocument(doc) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FolderItem(folder: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(6.dp).clickable { onClick() },
        elevation = 4.dp
    ) {
        Row(Modifier.padding(16.dp)) {
            Icon(Icons.Default.Folder, contentDescription = null)
            Spacer(Modifier.width(12.dp))
            Text(folder)
        }
    }
}

@Composable
fun DocumentItem(
    document: DocumentEntity,
    onOpen: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(6.dp).clickable { onOpen() },
        elevation = 4.dp
    ) {
        Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Row {
                Icon(Icons.Default.InsertDriveFile, contentDescription = null)
                Spacer(Modifier.width(12.dp))

                Column {
                    Text(document.fileName)
                    document.folderName?.let {
                        Text("Folder: $it", style = MaterialTheme.typography.caption)
                    }
                }
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}
