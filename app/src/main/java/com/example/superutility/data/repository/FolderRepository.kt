package com.example.superutility.data.repository

import com.example.superutility.data.room.dao.FolderDao
import com.example.superutility.data.room.entities.FolderEntity
import kotlinx.coroutines.flow.Flow

class FolderRepository(
    private val dao: FolderDao
) {

    fun getAllFolders(): Flow<List<FolderEntity>> = dao.getAllFolders()

    suspend fun addFolder(entity: FolderEntity) {
        dao.insertFolder(entity)
    }

    suspend fun updateFolder(entity: FolderEntity) {
        dao.updateFolder(entity)
    }

    suspend fun deleteFolder(entity: FolderEntity) {
        dao.deleteFolder(entity)
    }

    suspend fun getFolder(id: Int): FolderEntity? {
        return dao.getFolderById(id)
    }
}
