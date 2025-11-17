package com.example.superutility.models


data class AssignmentUI(
    val id: String,
    var title: String,
    var subject: String,
    var dueInDays: Int,
    var attachedName: String? = null
)

data class NoteUI(
    val id: String,
    var content: String,
    val timestamp: Long
)

data class ExpenseUI(
    val id: String,
    var title: String,
    var amount: Double,
    var category: String,
    val timestamp: Long
)
