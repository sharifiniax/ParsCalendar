package com.sharifiniax.parscalendar.repository

import androidx.room.Update
import com.sharifiniax.parscalendar.data.CategoryWithTasks
import com.sharifiniax.parscalendar.data.Task
import com.sharifiniax.parscalendar.data.TodoCategory
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {


    suspend fun insert(category: TodoCategory)

    suspend fun getId(name:String):TodoCategory?

    fun getAll(): Flow<List<TodoCategory>>

    suspend fun updateCategory(category: TodoCategory)

    suspend fun delete(category: TodoCategory)

    suspend fun findById(id:Int): TodoCategory?
    suspend fun contain(name: String): TodoCategory?


}