package com.example.superutility.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.superutility.data.repository.AssignmentRepository
import com.example.superutility.data.room.entities.AssignmentEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AssignmentViewModel(
    private val repository: AssignmentRepository
) : ViewModel() {

    val assignments = repository.assignments
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun addAssignment(title: String, desc: String?) {
        val newEntity = AssignmentEntity(
            title = title,
            description = desc,
            timestamp = System.currentTimeMillis()
        )
        viewModelScope.launch { repository.addAssignment(newEntity) }
    }

    fun updateAssignment(updated: AssignmentEntity) {
        viewModelScope.launch { repository.updateAssignment(updated) }
    }

    fun deleteAssignment(assignment: AssignmentEntity) {
        viewModelScope.launch { repository.deleteAssignment(assignment) }
    }

    suspend fun getAssignment(id: Int): AssignmentEntity? {
        return repository.getAssignment(id)
    }
}

class AssignmentViewModelFactory(
    private val repository: AssignmentRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AssignmentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AssignmentViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
