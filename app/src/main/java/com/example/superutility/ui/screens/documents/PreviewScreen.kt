package com.example.superutility.ui.screens.documents

import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.superutility.utils.openDocument
import java.io.File

// ----------------------------------------------------------
// MAIN PREVIEW SCREEN — FULL SCREEN + TOP BAR + PREVIEW HANDLER
// ----------------------------------------------------------
@Composable
fun PreviewScreen(filePath: String, mimeType: String?) {

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Preview") })
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            when {
                mimeType?.startsWith("image/") == true ->
                    ImagePreview(filePath)

                mimeType?.contains("pdf") == true ->
                    PdfPreview(filePath)

                mimeType?.startsWith("text/") == true ||
                        filePath.endsWith(".txt", true) ||
                        filePath.endsWith(".json", true) ||
                        filePath.endsWith(".md", true) ||
                        filePath.endsWith(".xml", true) ||
                        filePath.endsWith(".csv", true) ->
                    TextPreview(filePath)

                else ->
                    UnsupportedPreview(filePath, mimeType)
            }
        }
    }
}

//////////////////////////////////////////////////////////////
//                 PDF PREVIEW (Zoom + Pages + Full Screen)
//////////////////////////////////////////////////////////////
@Composable
fun PdfPreview(path: String) {

    val file = remember(path) { File(path) }

    var renderer by remember { mutableStateOf<PdfRenderer?>(null) }
    var fd by remember { mutableStateOf<ParcelFileDescriptor?>(null) }

    var currentPage by remember { mutableStateOf(0) }
    var totalPages by remember { mutableStateOf(1) }

    var scale by remember { mutableStateOf(1f) }

    // Load PDF Renderer
    DisposableEffect(path) {
        try {
            fd = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
            renderer = PdfRenderer(fd!!)
            totalPages = renderer!!.pageCount
        } catch (e: Exception) {
            e.printStackTrace()
        }

        onDispose {
            renderer?.close()
            fd?.close()
        }
    }

    if (renderer == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    // Render current page to bitmap with zoom
    val bmp = remember(currentPage, scale) {
        val page = renderer!!.openPage(currentPage)
        val bitmap = android.graphics.Bitmap.createBitmap(
            (page.width * scale).toInt(),
            (page.height * scale).toInt(),
            android.graphics.Bitmap.Config.ARGB_8888
        )
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        page.close()
        bitmap
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        // Zoom Slider
        Slider(
            value = scale,
            onValueChange = { new -> scale = new },
            valueRange = 1f..3f,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // PDF Page Image (full width)
        Image(
            bitmap = bmp.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        )

        // Page Navigation
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { if (currentPage > 0) currentPage-- },
                enabled = currentPage > 0
            ) { Text("Previous") }

            Text("Page ${currentPage + 1} / $totalPages")

            Button(
                onClick = { if (currentPage < totalPages - 1) currentPage++ },
                enabled = currentPage < totalPages - 1
            ) { Text("Next") }
        }
    }
}

//////////////////////////////////////////////////////////////
//        IMAGE PREVIEW (Pinch Zoom + Pan + Full Screen)
//////////////////////////////////////////////////////////////
@Composable
fun ImagePreview(path: String) {

    var scale by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    val gestureModifier = Modifier.pointerInput(Unit) {
        detectTransformGestures { _, pan, zoom, _ ->
            scale = (scale * zoom).coerceIn(1f, 4f)
            offsetX += pan.x
            offsetY += pan.y
        }
    }

    AsyncImage(
        model = path,
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()   // FULL SCREEN
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                translationX = offsetX,
                translationY = offsetY
            )
            .then(gestureModifier)
            .padding(10.dp)
    )
}

//////////////////////////////////////////////////////////////
//                      TEXT PREVIEW (Full Screen Scroll)
//////////////////////////////////////////////////////////////
@Composable
fun TextPreview(path: String) {
    val text = remember(path) {
        try { File(path).readText() }
        catch (e: Exception) { "Error loading file" }
    }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(text)
    }
}

//////////////////////////////////////////////////////////////
//                UNSUPPORTED PREVIEW (Fallback)
//////////////////////////////////////////////////////////////
@Composable
fun UnsupportedPreview(path: String, mime: String?) {
    val context = LocalContext.current

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Preview not available.")
        Spacer(Modifier.height(10.dp))
        Button(onClick = { openDocument(context, path, mime) }) {
            Text("Open Externally")
        }
    }
}
