package com.example.superutility.data.repository

import com.example.superutility.data.room.dao.NotesDao
import com.example.superutility.data.room.entities.NoteEntity
import kotlinx.coroutines.flow.Flow

class NotesRepository(private val dao: NotesDao) {

    val notes: Flow<List<NoteEntity>> = dao.getAllNotes()

    suspend fun addNote(note: NoteEntity) {
        dao.insertNote(note)
    }

    suspend fun getNote(id: Int): NoteEntity? {
        return dao.getNoteById(id)
    }

    suspend fun updateNote(note: NoteEntity) {
        dao.updateNote(note)
    }

    suspend fun deleteNote(note: NoteEntity) {
        dao.deleteNote(note)
    }
}
