package com.example.superutility.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.superutility.data.repository.DocumentsRepository
import com.example.superutility.data.room.entities.DocumentEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DocumentsViewModel(
    private val repository: DocumentsRepository
) : ViewModel() {

    val allDocuments: StateFlow<List<DocumentEntity>> = repository.allDocuments
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun getDocumentsInFolder(folderName: String?) = repository.getDocumentsInFolder(folderName)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allFolders = repository.allFolders
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addDocument(fileName: String, filePath: String, folderName: String?, mimeType: String?) {
        val entity = DocumentEntity(
            fileName = fileName,
            folderName = folderName,
            filePath = filePath,
            mimeType = mimeType
        )
        viewModelScope.launch {
            repository.addDocument(entity)
        }
    }

    fun updateDocument(entity: DocumentEntity) {
        viewModelScope.launch {
            repository.updateDocument(entity)
        }
    }

    fun deleteDocument(entity: DocumentEntity) {
        viewModelScope.launch {
            repository.deleteDocument(entity)
        }
    }
}

class DocumentsViewModelFactory(
    private val repository: DocumentsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DocumentsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DocumentsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
