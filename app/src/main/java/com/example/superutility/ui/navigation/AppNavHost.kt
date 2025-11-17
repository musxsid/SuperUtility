package com.example.superutility.navigation
import com.example.superutility.ui.screens.notes.AddNoteScreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.superutility.ui.screens.assignments.AddAssignmentScreen
import com.example.superutility.ui.screens.assignments.AssignmentsScreen
import com.example.superutility.ui.screens.assignments.EditAssignmentScreen
import com.example.superutility.ui.screens.dashboard.DashboardScreen
import com.example.superutility.ui.screens.documents.AddDocumentScreen
import com.example.superutility.ui.screens.documents.DocumentsScreen
import com.example.superutility.ui.screens.expenses.ExpensesScreen
import com.example.superutility.ui.screens.gpa.GpaScreen
import com.example.superutility.ui.screens.notes.EditNoteScreen
import com.example.superutility.ui.screens.notes.NotesScreen
import com.example.superutility.ui.screens.studyspace.StudySpaceScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "dashboard"
    ) {

        // Add your routes INSIDE this block

        composable("dashboard") { DashboardScreen(navController) }

        composable("assignments") { AssignmentsScreen(navController) }

        composable("add_assignment") { AddAssignmentScreen(navController) }

        composable("edit_assignment/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
            EditAssignmentScreen(navController, id)
        }

        composable("notes") { NotesScreen(navController) }

        composable("add_note") { AddNoteScreen(navController) }

        composable("edit_note/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
            EditNoteScreen(navController, id)
        }
        composable("add_document") {
            AddDocumentScreen(navController)
        }

        composable("documents") { DocumentsScreen(navController) }
        composable("add_document") { AddDocumentScreen(navController) }

        composable("studyspace") { StudySpaceScreen(navController) }
        composable("documents") { DocumentsScreen(navController) }
        composable("gpa") { GpaScreen(navController) }
        composable("expenses") { ExpensesScreen(navController)
        }
    }
}
