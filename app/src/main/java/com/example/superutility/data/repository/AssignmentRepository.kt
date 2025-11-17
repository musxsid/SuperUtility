package com.example.superutility.data.repository

import com.example.superutility.data.room.dao.AssignmentDao
import com.example.superutility.data.room.entities.AssignmentEntity
import kotlinx.coroutines.flow.Flow

class AssignmentRepository(private val dao: AssignmentDao) {

    val assignments: Flow<List<AssignmentEntity>> = dao.getAllAssignments()

    suspend fun addAssignment(entity: AssignmentEntity) {
        dao.insertAssignment(entity)
    }

    suspend fun updateAssignment(entity: AssignmentEntity) {
        dao.updateAssignment(entity)
    }

    suspend fun deleteAssignment(entity: AssignmentEntity) {
        dao.deleteAssignment(entity)
    }

    suspend fun getAssignment(id: Int): AssignmentEntity? {
        return dao.getAssignmentById(id)
    }
}
