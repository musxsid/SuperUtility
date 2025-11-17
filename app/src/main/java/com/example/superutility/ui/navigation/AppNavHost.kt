package com.example.superutility.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.superutility.ui.screens.assignments.AddAssignmentScreen
import com.example.superutility.ui.screens.assignments.AssignmentsScreen
import com.example.superutility.ui.screens.dashboard.DashboardScreen
import com.example.superutility.ui.screens.documents.DocumentsScreen
import com.example.superutility.ui.screens.expenses.ExpensesScreen
import com.example.superutility.ui.screens.gpa.GpaScreen
import com.example.superutility.ui.screens.notes.NotesScreen
import com.example.superutility.ui.screens.studyspace.StudySpaceScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") { DashboardScreen(navController) }
        composable("assignments") { AssignmentsScreen(navController) }
        composable("add_assignment") { AddAssignmentScreen(navController) }
        composable("studyspace") { StudySpaceScreen(navController) }
        composable("documents") { DocumentsScreen(navController) }
        composable("gpa") { GpaScreen(navController) }
        composable("expenses") { ExpensesScreen(navController) }
        composable("notes") { NotesScreen(navController) }
    }
}
