package com.example.superutility.data.room.dao

import androidx.room.*
import com.example.superutility.data.room.entities.DocumentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DocumentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDocument(doc: DocumentEntity)

    @Update
    suspend fun updateDocument(doc: DocumentEntity)

    @Delete
    suspend fun deleteDocument(doc: DocumentEntity)

    @Query("SELECT * FROM documents ORDER BY timestamp DESC")
    fun getAllDocuments(): Flow<List<DocumentEntity>>

    @Query("SELECT * FROM documents WHERE folderName = :folderName ORDER BY timestamp DESC")
    fun getDocumentsByFolder(folderName: String?): Flow<List<DocumentEntity>>

    @Query("SELECT * FROM documents WHERE folderName IS NULL ORDER BY timestamp DESC")
    fun getUnsortedDocuments(): Flow<List<DocumentEntity>>

    @Query("SELECT DISTINCT folderName FROM documents")
    fun getAllFolders(): Flow<List<String?>>
}
