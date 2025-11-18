package com.example.superutility.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.superutility.ui.screens.dashboard.DashboardScreen
import com.example.superutility.ui.screens.documents.*
import com.example.superutility.ui.screens.assignments.*
import com.example.superutility.ui.screens.notes.*
import com.example.superutility.ui.screens.expenses.ExpensesScreen
import com.example.superutility.ui.screens.gpa.GpaScreen
import com.example.superutility.ui.screens.studyspace.StudySpaceScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "dashboard"
    ) {

        composable("preview/{path}/{mime}") { backStackEntry ->
            val encodedPath = backStackEntry.arguments?.getString("path")!!
            val encodedMime = backStackEntry.arguments?.getString("mime")

            val path = Uri.decode(encodedPath)
            val mime = encodedMime?.let { Uri.decode(it) }

            PreviewScreen(path, mime)
        }

        composable("dashboard") { DashboardScreen(navController) }

        composable("assignments") { AssignmentsScreen(navController) }
        composable("add_assignment") { AddAssignmentScreen(navController) }
        composable("edit_assignment/{id}") { entry ->
            val id = entry.arguments?.getString("id")?.toInt() ?: 0
            EditAssignmentScreen(navController, id)
        }

        composable("notes") { NotesScreen(navController) }
        composable("add_note") { AddNoteScreen(navController) }
        composable("edit_note/{id}") { entry ->
            val id = entry.arguments?.getString("id")?.toInt() ?: 0
            EditNoteScreen(navController, id)
        }

        composable("documents") { DocumentsScreen(navController) }
        composable("add_document") { AddDocumentScreen(navController) }

        composable("folder/{folderName}") { entry ->
            val folderName = entry.arguments?.getString("folderName") ?: "Unknown"
            FolderScreen(navController, folderName)
        }

        composable("studyspace") { StudySpaceScreen(navController) }
        composable("gpa") { GpaScreen(navController) }
        composable("expenses") { ExpensesScreen(navController) }
    }
}
