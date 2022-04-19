package com.sharifiniax.parscalendar.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun insert(category: TodoCategory)

    @Query("SELECT * FROM TodoCategory")
    fun getAll(): Flow<List<TodoCategory>>

    @Delete
    suspend fun delete(category: TodoCategory)

    @Query("SELECT * FROM TodoCategory T WHERE T.categoryId ==:id")
    suspend fun findById(id:Int):TodoCategory?

    @Query("SELECT * FROM TodoCategory T WHERE T.name == :name")
    suspend fun getId(name:String):TodoCategory?

    @Update
    suspend fun updateCategory(category:TodoCategory)

    @Query("Select * From TodoCategory T WHERE T.name==:name")
    suspend fun contain(name: String): TodoCategory?

}