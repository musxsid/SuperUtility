package com.example.superutility.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Note
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Calculate
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
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.primary
        )

        Text(
            text = "All student tools in one place",
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

            // ----------- ROW 1 -----------
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                DashboardTile(
                    title = "Assignments",
                    icon = Icons.AutoMirrored.Filled.List,
                    colors = listOf(Color(0xFFBBDEFB), Color(0xFF90CAF9)),
                    modifier = Modifier.weight(1f)
                ) { navController.navigate("assignments") }

                DashboardTile(
                    title = "Study Space",
                    icon = Icons.Filled.Timer,
                    colors = listOf(Color(0xFFFFF9C4), Color(0xFFFFF59D)),
                    modifier = Modifier.weight(1f)
                ) { navController.navigate("studyspace") }
            }

            // ----------- ROW 2 -----------
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                DashboardTile(
                    title = "Notes",
                    icon = Icons.AutoMirrored.Filled.Note,
                    colors = listOf(Color(0xFFE1BEE7), Color(0xFFD1C4E9)),
                    modifier = Modifier.weight(1f)
                ) { navController.navigate("notes") }

                DashboardTile(
                    title = "Documents",
                    icon = Icons.Filled.Receipt,
                    colors = listOf(Color(0xFFC8E6C9), Color(0xFFA5D6A7)),
                    modifier = Modifier.weight(1f)
                ) { navController.navigate("documents") }
            }

            // ----------- ROW 3 -----------
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                DashboardTile(
                    title = "Expenses",
                    icon = Icons.Filled.AttachMoney,
                    colors = listOf(Color(0xFFFFE0B2), Color(0xFFFFCC80)),
                    modifier = Modifier.weight(1f)
                ) { navController.navigate("expenses") }

                DashboardTile(
                    title = "GPA Calc",
                    icon = Icons.Filled.Calculate,
                    colors = listOf(Color(0xFFBBDEFB), Color(0xFF90CAF9)),
                    modifier = Modifier.weight(1f)
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
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(120.dp)
            .shadow(6.dp, shape = RoundedCornerShape(14.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        elevation = 6.dp
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
