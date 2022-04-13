package com.sharifiniax.parscalendar.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity
data class TodoCategory(
    @PrimaryKey
    val categoryId:Int,

    val name:String,
    val color: CategoryColor = CategoryColor.White
)






