package com.sharifiniax.parscalendar.data

import androidx.lifecycle.MutableLiveData
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
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
    val priority:Int=1,
    var done:Boolean=false,
    val comment:String?=null
){
    @PrimaryKey(autoGenerate = true)
    var taskId:Int=0

    @Ignore
    var expand:Boolean = false

}