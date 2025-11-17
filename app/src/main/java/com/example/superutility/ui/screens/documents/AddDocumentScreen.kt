package com.example.superutility.ui.screens.documents

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.superutility.SuperUtilityApp
import com.example.superutility.viewmodels.DocumentsViewModel
import com.example.superutility.viewmodels.DocumentsViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

@Composable
fun AddDocumentScreen(
    navController: NavController,
    viewModel: DocumentsViewModel = viewModel(
        factory = DocumentsViewModelFactory(
            (LocalContext.current.applicationContext as SuperUtilityApp).documentsRepository
        )
    )
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var pickedUri by remember { mutableStateOf<Uri?>(null) }
    var displayName by remember { mutableStateOf("") }
    var folderName by remember { mutableStateOf("") }
    var mimeType by remember { mutableStateOf<String?>(null) }
    var busy by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        if (uri != null) {
            pickedUri = uri
            mimeType = context.contentResolver.getType(uri)
            displayName = queryDisplayName(context.contentResolver, uri) ?: "file"
        }
    }

    Column(Modifier.padding(16.dp)) {

        Text("Add document", style = MaterialTheme.typography.h6)
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = displayName,
            onValueChange = { displayName = it },
            label = { Text("File name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = folderName,
            onValueChange = { folderName = it },
            label = { Text("Folder (optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Row {
            Button(onClick = { launcher.launch(arrayOf("*/*")) }) {
                Text("Pick file")
            }
            Spacer(Modifier.width(8.dp))
            Text(if (pickedUri != null) "Picked" else "No file picked")
        }

        Spacer(Modifier.height(16.dp))

        if (busy) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
        }

        Button(
            onClick = {
                if (pickedUri == null) {
                    message = "Pick a file first"
                    return@Button
                }

                busy = true
                message = null

                val uri = pickedUri!!
                val name = displayName.ifBlank {
                    queryDisplayName(context.contentResolver, uri) ?: "file"
                }

                scope.launch {
                    val savedPath = copyUriToInternal(context, uri, name)

                    viewModel.addDocument(
                        fileName = name,
                        filePath = savedPath,
                        folderName = folderName.ifBlank { null },
                        mimeType = mimeType
                    )

                    busy = false
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }

        Spacer(Modifier.height(8.dp))

        message?.let { Text(it, color = MaterialTheme.colors.error) }
    }
}


/** Helpers **/
private fun queryDisplayName(resolver: ContentResolver, uri: Uri): String? {
    resolver.query(uri, arrayOf(OpenableColumns.DISPLAY_NAME), null, null, null)?.use { cursor ->
        if (cursor.moveToFirst()) {
            val idx = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (idx >= 0) return cursor.getString(idx)
        }
    }
    return null
}

private suspend fun copyUriToInternal(context: Context, uri: Uri, fileName: String): String =
    withContext(Dispatchers.IO) {
        val docsDir = File(context.filesDir, "documents")
        if (!docsDir.exists()) docsDir.mkdirs()

        val target = File(docsDir, fileName)

        context.contentResolver.openInputStream(uri).use { input ->
            FileOutputStream(target).use { out ->
                if (input != null) {
                    val buf = ByteArray(8192)
                    var len: Int
                    while (input.read(buf).also { len = it } > 0) {
                        out.write(buf, 0, len)
                    }
                }
            }
        }
        target.absolutePath
    }
