package com.example.superutility.ui.screens.expenses

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

data class ExpenseUI(val title: String, val amount: Double, val category: String)

@Composable
fun ExpensesScreen(navController: NavController) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }

    val expenses = remember { mutableStateListOf<ExpenseUI>() }

    Scaffold(topBar = { TopAppBar(title = { Text("Expenses") }) }) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)
            .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("Amount") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Category") }, modifier = Modifier.fillMaxWidth())

            Button(onClick = {
                val amt = amount.toDoubleOrNull() ?: 0.0
                expenses.add(ExpenseUI(title, amt, category))
                title = ""; amount = ""; category = ""
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Add Expense")
            }

            Divider()

            if (expenses.isEmpty()) {
                Text("No expenses recorded.")
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(expenses) { e ->
                        Card(modifier = Modifier.fillMaxWidth(), elevation = 4.dp, shape = RoundedCornerShape(8.dp)) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(e.title, style = MaterialTheme.typography.subtitle1)
                                Text("₹ ${e.amount}", style = MaterialTheme.typography.body2)
                                Text(e.category, style = MaterialTheme.typography.caption)
                            }
                        }
                    }
                }
            }
        }
    }
}
