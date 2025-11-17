package com.example.superutility.ui.screens.documents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun DocumentsScreen(navController: NavController) {
    val folders = listOf("ID Proofs", "Certificates", "College Docs", "Bills & Receipts", "Personal Docs")
    val colors = listOf(Color(0xFFBBDEFB), Color(0xFFFFF9C4), Color(0xFFE1BEE7), Color(0xFFC8E6C9), Color(0xFFFFE0B2))

    Column(modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Documents", style = MaterialTheme.typography.h6)
        Divider()
        folders.forEachIndexed { idx, f ->
            Card(modifier = Modifier.fillMaxWidth().clickable { /* open folder later */ }, elevation = 4.dp) {
                Box(modifier = Modifier.padding(14.dp).fillMaxWidth()) {
                    Text(f, color = Color.Black)
                }
            }
        }
    }
}
