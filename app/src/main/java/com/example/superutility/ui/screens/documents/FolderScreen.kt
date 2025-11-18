package com.example.superutility.ui.screens.documents

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
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
fun FolderScreen(navController: NavController, folderName: String) {

    val context = LocalContext.current
    val app = context.applicationContext as SuperUtilityApp
    val viewModel: DocumentsViewModel = viewModel(
        factory = DocumentsViewModelFactory(app.documentsRepository)
    )

    val documents by viewModel.getDocumentsInFolder(folderName).collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(folderName) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->

        if (documents.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No documents in this folder.")
            }
        } else {
            LazyColumn(Modifier.padding(padding).padding(16.dp)) {
                items(documents) { doc ->
                    FolderDocumentItem(
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

@Composable
fun FolderDocumentItem(
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
                Text(document.fileName)
            }

            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}
