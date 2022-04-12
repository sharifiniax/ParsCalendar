package com.sharifiniax.parscalendar.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sharifiniax.parscalendar.data.CategoryWithTasks
import com.sharifiniax.parscalendar.data.Task
import kotlinx.coroutines.flow.Flow
import java.util.*

interface TodoRepository {



    suspend fun insert(task: Task)


    suspend fun getAll():List<Task>


    suspend fun delete(task: Task)


    suspend fun findById(id:Int): Task?


    fun getCategoriesWithTasks(): Flow<List<CategoryWithTasks>>


}