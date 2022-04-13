package com.sharifiniax.parscalendar.data

import androidx.room.*
import com.google.android.material.circularreveal.CircularRevealHelper
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun insert(task: Task)

    @Query("SELECT * FROM Task")
    fun getAll():Flow<List<Task>>

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM TASK T WHERE T.taskId ==:id")
    suspend fun findById(id:Int):Task?

    @Transaction
    @Query("SELECT * FROM TodoCategory")
    fun getCategoriesWithTasks(): Flow<List<CategoryWithTasks>>




}