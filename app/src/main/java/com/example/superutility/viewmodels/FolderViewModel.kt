package com.example.superutility.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.superutility.data.repository.FolderRepository
import com.example.superutility.data.room.entities.FolderEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FolderViewModel(
    private val repository: FolderRepository
) : ViewModel() {

    // List of all folders
    val folders = repository.getAllFolders()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    // Add folder
    fun addFolder(name: String) {
        viewModelScope.launch {
            repository.addFolder(FolderEntity(name = name))
        }
    }

    // Rename folder
    fun updateFolder(folder: FolderEntity) {
        viewModelScope.launch {
            repository.updateFolder(folder)
        }
    }

    // Delete folder
    fun deleteFolder(folder: FolderEntity) {
        viewModelScope.launch {
            repository.deleteFolder(folder)
        }
    }
}

class FolderViewModelFactory(
    private val repository: FolderRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FolderViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FolderViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
