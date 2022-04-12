package com.sharifiniax.parscalendar.repository

import com.sharifiniax.parscalendar.data.CategoryWithTasks
import com.sharifiniax.parscalendar.data.Task
import com.sharifiniax.parscalendar.data.TaskDao
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

@ViewModelScoped
class TodoRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
):TodoRepository {
    override suspend fun insert(task: Task) {
       taskDao.insert(task)
    }

    override suspend fun getAll(): List<Task> {
        return taskDao.getAll()
    }

    override suspend fun delete(task: Task) {
        taskDao.delete(task)
    }

    override suspend fun findById(id: Int): Task? {
        return taskDao.findById(id)
    }

    override fun getCategoriesWithTasks(): Flow<List<CategoryWithTasks>> {
        return taskDao.getCategoriesWithTasks()
    }


}