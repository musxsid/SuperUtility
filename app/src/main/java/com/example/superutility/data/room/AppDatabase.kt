package com.example.superutility.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.superutility.data.room.dao.AssignmentDao
import com.example.superutility.data.room.dao.DocumentDao
import com.example.superutility.data.room.dao.NotesDao
import com.example.superutility.data.room.dao.FolderDao
import com.example.superutility.data.room.entities.AssignmentEntity
import com.example.superutility.data.room.entities.NoteEntity
import com.example.superutility.data.room.entities.DocumentEntity
import com.example.superutility.data.room.entities.FolderEntity

@Database(
    entities = [
        AssignmentEntity::class,
        NoteEntity::class,
        DocumentEntity::class,
        FolderEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun assignmentDao(): AssignmentDao
    abstract fun notesDao(): NotesDao
    abstract fun folderDao(): FolderDao

    abstract fun documentDao(): DocumentDao// ✔ MUST MATCH

}
