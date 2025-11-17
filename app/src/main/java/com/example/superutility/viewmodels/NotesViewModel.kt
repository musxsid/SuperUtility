package com.example.superutility.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.superutility.data.repository.NotesRepository
import com.example.superutility.data.room.entities.NoteEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NotesViewModel(
    private val repository: NotesRepository
) : ViewModel() {

    val notes = repository.notes
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            val note = NoteEntity(
                title = title,
                content = content,
                timestamp = System.currentTimeMillis()
            )
            repository.addNote(note)
        }
    }

    suspend fun getNote(id: Int): NoteEntity? {
        return repository.getNote(id)
    }

    fun updateNote(note: NoteEntity) {
        viewModelScope.launch { repository.updateNote(note) }
    }

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch { repository.deleteNote(note) }
    }
}

class NotesViewModelFactory(
    private val repository: NotesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
