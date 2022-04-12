package com.sharifiniax.parscalendar.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.material.circularreveal.CircularRevealHelper

@Entity
data class Task(
    val title:String,
    val description:String?,
    @Embedded
    val day: DayModel,
    val ownerCategoryId:Int,
    val tag:String?=null,
    val priority:Int=10,
    var done:Boolean=false,
    val comment:String?=null
){
    @PrimaryKey(autoGenerate = true)
    var taskId:Int=0

}