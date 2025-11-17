package com.example.superutility.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun DashboardScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Text(
            text = "Welcome to SuperUtility",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = "All student tools in one place",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                DashboardTile(
                    title = "Assignments",
                    icon = Icons.Default.List,
                    colors = listOf(Color(0xFFBBDEFB), Color(0xFF90CAF9))
                ) { navController.navigate("assignments") }

                DashboardTile(
                    title = "Study Space",
                    icon = Icons.Default.Timer,
                    colors = listOf(Color(0xFFFFF9C4), Color(0xFFFFF59D))
                ) { navController.navigate("studyspace") }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                DashboardTile(
                    title = "Notes",
                    icon = Icons.Default.Note,
                    colors = listOf(Color(0xFFE1BEE7), Color(0xFFD1C4E9))
                ) { navController.navigate("notes") }

                DashboardTile(
                    title = "Documents",
                    icon = Icons.Default.Receipt,
                    colors = listOf(Color(0xFFC8E6C9), Color(0xFFA5D6A7))
                ) { navController.navigate("documents") }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                DashboardTile(
                    title = "Expenses",
                    icon = Icons.Default.AttachMoney,
                    colors = listOf(Color(0xFFFFE0B2), Color(0xFFFFCC80))
                ) { navController.navigate("expenses") }

                DashboardTile(
                    title = "GPA Calc",
                    icon = Icons.Default.Calculate,
                    colors = listOf(Color(0xFFBBDEFB), Color(0xFF90CAF9))
                ) { navController.navigate("gpa") }
            }
        }
    }
}

@Composable
fun DashboardTile(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    colors: List<Color>,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .weight(1f)
            .height(120.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(
            modifier = Modifier
                .background(Brush.verticalGradient(colors))
                .fillMaxSize()
                .padding(14.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = title, tint = Color(0xFF0D47A1))
                Spacer(Modifier.width(12.dp))
                Column {
                    Text(title, color = Color(0xFF0D47A1), fontSize = 16.sp)
                    Text("Open", color = Color(0xFF0D47A1).copy(alpha = 0.8f), fontSize = 12.sp)
                }
            }
        }
    }
}
