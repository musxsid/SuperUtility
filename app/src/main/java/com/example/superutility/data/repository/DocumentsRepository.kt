package com.example.superutility.data.repository

import com.example.superutility.data.room.dao.DocumentDao
import com.example.superutility.data.room.entities.DocumentEntity
import kotlinx.coroutines.flow.Flow

class DocumentsRepository(
    private val dao: DocumentDao
) {

    val allDocuments: Flow<List<DocumentEntity>> = dao.getAllDocuments()
    val unsortedDocuments: Flow<List<DocumentEntity>> = dao.getUnsortedDocuments()
    val allFolders: Flow<List<String?>> = dao.getAllFolders()

    // For folder screen compatibility
    fun getDocumentsByFolder(folderName: String): Flow<List<DocumentEntity>> {
        return dao.getDocumentsByFolder(folderName)
    }

    // Old function (still safe to keep)
    fun getDocumentsInFolder(folderName: String?): Flow<List<DocumentEntity>> {
        return dao.getDocumentsByFolder(folderName)
    }

    suspend fun addDocument(entity: DocumentEntity) {
        dao.insertDocument(entity)
    }

    suspend fun updateDocument(entity: DocumentEntity) {
        dao.updateDocument(entity)
    }

    suspend fun deleteDocument(entity: DocumentEntity) {
        dao.deleteDocument(entity)
    }
}
