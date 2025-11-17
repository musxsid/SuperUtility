package com.example.superutility.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assignments")
data class AssignmentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)
