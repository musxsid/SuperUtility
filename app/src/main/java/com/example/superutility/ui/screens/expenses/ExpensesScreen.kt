package com.example.superutility.ui.screens.expenses

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class ExpenseUI(val item: String, val amount: Double)

@Composable
fun ExpensesScreen(navController: androidx.navigation.NavController) {
    var item by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    val list = remember { mutableStateListOf<ExpenseUI>() }

    Scaffold(topBar = { TopAppBar(title = { Text("Expenses") }) }) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(value = item, onValueChange = { item = it }, label = { Text("Item") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("Amount") }, modifier = Modifier.fillMaxWidth())

            Button(onClick = {
                val a = amount.toDoubleOrNull()
                if (item.isNotBlank() && a != null) {
                    list.add(ExpenseUI(item, a))
                    item = ""
                    amount = ""
                }
            }, modifier = Modifier.fillMaxWidth()) { Text("Add Expense") }

            Divider()

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(list) { e ->
                    Card(modifier = Modifier.fillMaxWidth(), elevation = 4.dp) {
                        Row(modifier = Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(e.item)
                            Text("₹%.2f".format(e.amount))
                        }
                    }
                }
            }
        }
    }
}
