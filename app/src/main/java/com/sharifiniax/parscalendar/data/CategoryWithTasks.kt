package com.sharifiniax.parscalendar.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation


data class CategoryWithTasks(
    @Embedded val category: TodoCategory,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "ownerCategoryId"
    )
    val tasks:List<Task>
)