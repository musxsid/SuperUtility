package com.example.superutility.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "documents")
data class DocumentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fileName: String,
    val folderName: String? = null,   // null -> root / unassigned
    val filePath: String,             // app-internal path where file was copied
    val mimeType: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)
