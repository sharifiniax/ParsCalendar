package com.sharifiniax.parscalendar.repository

import com.sharifiniax.parscalendar.data.CategoryDao
import com.sharifiniax.parscalendar.data.TodoCategory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao:CategoryDao
):CategoryRepository {


    override suspend fun insert(category: TodoCategory) {
        categoryDao.insert(category)
    }

    override suspend fun getId(name: String):TodoCategory? {
        return categoryDao.getId(name)
    }

    override fun getAll(): Flow<List<TodoCategory>> {
        return categoryDao.getAll()
    }

    override suspend fun updateCategory(category: TodoCategory) {
        categoryDao.updateCategory(category)
    }

    override suspend fun delete(category: TodoCategory) {
        categoryDao.delete(category)
    }

    override suspend fun findById(id: Int): TodoCategory? {
        return categoryDao.findById(id)
    }

    override suspend fun contain(name: String): TodoCategory? {
        return categoryDao.contain(name)
    }
}