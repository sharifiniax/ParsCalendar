package com.sharifiniax.parscalendar.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity
data class TodoCategory(
    val name:String = "Inbox",
    val color: CategoryColor = CategoryColor.White
){

    @PrimaryKey(autoGenerate = true)
    var categoryId:Int=0

}





