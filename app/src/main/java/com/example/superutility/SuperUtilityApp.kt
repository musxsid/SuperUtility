package com.example.superutility

import android.app.Application
import androidx.room.Room
import com.example.superutility.data.repository.AssignmentRepository
import com.example.superutility.data.repository.NotesRepository
import com.example.superutility.data.repository.DocumentsRepository
import com.example.superutility.data.room.AppDatabase

class SuperUtilityApp : Application() {

    lateinit var db: AppDatabase
        private set

    lateinit var assignmentRepository: AssignmentRepository
        private set

    lateinit var notesRepository: NotesRepository
        private set

    lateinit var documentsRepository: DocumentsRepository
        private set

    override fun onCreate() {
        super.onCreate()

        // Build DB
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "superutility_db"
        )
            .fallbackToDestructiveMigration()
            .build()

        // Repositories
        assignmentRepository = AssignmentRepository(db.assignmentDao())
        notesRepository = NotesRepository(db.notesDao())
        documentsRepository = DocumentsRepository(db.documentDao())   // <-- FIXED
    }
}
