package com.example.superutility.data.room.dao

import androidx.room.*
import com.example.superutility.data.room.entities.AssignmentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AssignmentDao {

    @Insert
    suspend fun insertAssignment(assignment: AssignmentEntity)

    @Update
    suspend fun updateAssignment(assignment: AssignmentEntity)

    @Delete
    suspend fun deleteAssignment(assignment: AssignmentEntity)

    @Query("SELECT * FROM assignments ORDER BY timestamp DESC")
    fun getAllAssignments(): Flow<List<AssignmentEntity>>

    @Query("SELECT * FROM assignments WHERE id = :id LIMIT 1")
    suspend fun getAssignmentById(id: Int): AssignmentEntity?
}
