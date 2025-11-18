package com.example.superutility.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.superutility.data.repository.FolderRepository
import com.example.superutility.data.room.entities.FolderEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FolderViewModel(
    private val repository: FolderRepository
) : ViewModel() {

    // ================================
    //  LIST OF ALL FOLDERS
    // ================================
    val allFolders: StateFlow<List<FolderEntity>> =
        repository.getAllFolders()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    // ================================
    //  ADD NEW FOLDER
    // ================================
    fun addFolder(name: String) {
        viewModelScope.launch {
            repository.addFolder(FolderEntity(name = name))
        }
    }

    // ================================
    //  UPDATE FOLDER (rename)
    // ================================
    fun updateFolder(folder: FolderEntity) {
        viewModelScope.launch {
            repository.updateFolder(folder)
        }
    }

    // ================================
    //  DELETE FOLDER
    // ================================
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
