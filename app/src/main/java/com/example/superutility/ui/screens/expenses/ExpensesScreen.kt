package com.example.superutility.ui.screens.expenses

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.superutility.models.ExpenseUI
import java.util.*

@Composable
fun ExpensesScreen(navController: androidx.navigation.NavController) {
    val expenses = remember { mutableStateListOf<ExpenseUI>() }
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Other") }

    Scaffold(topBar = { TopAppBar(title = { Text("Expenses") }) }) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, modifier = Modifier.weight(1f))
                OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("Amount") }, modifier = Modifier.width(120.dp))
            }

            OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Category") }, modifier = Modifier.fillMaxWidth())

            Button(onClick = {
                val amt = amount.toDoubleOrNull()
                if (title.isNotBlank() && amt != null) {
                    expenses.add(ExpenseUI(UUID.randomUUID().toString(), title, amt, category, System.currentTimeMillis()))
                    title = ""
                    amount = ""
                }
            }, modifier = Modifier.fillMaxWidth()) { Text("Add Expense") }

            Divider()

            if (expenses.isEmpty()) Text("No expenses yet.")
            else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(expenses) { e ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text("${e.title} • ₹${e.amount}", style = MaterialTheme.typography.subtitle1)
                                Text(e.category, style = MaterialTheme.typography.caption)
                            }
                        }
                    }
                }
            }
        }
    }
}
