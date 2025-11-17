package com.example.superutility.data.room.dao

import androidx.room.*
import com.example.superutility.data.room.entities.FolderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FolderDao {

    @Insert
    suspend fun insertFolder(folder: FolderEntity)

    @Delete
    suspend fun deleteFolder(folder: FolderEntity)

    @Update
    suspend fun updateFolder(folder: FolderEntity)

    @Query("SELECT * FROM folders ORDER BY createdAt DESC")
    fun getAllFolders(): Flow<List<FolderEntity>>

    @Query("SELECT * FROM folders WHERE id = :id LIMIT 1")
    suspend fun getFolderById(id: Int): FolderEntity?
}
