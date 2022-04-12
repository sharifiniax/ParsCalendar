package com.sharifiniax.parscalendar.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import com.sharifiniax.parscalendar.utils.Utils


data class DayModel(var dayOfWeek: Int, val dayOfMonth: Int, val month:Int, val year:Int,

                    val monthModel:MonthModel=MonthModel.Current
){
    override fun toString(): String {
        return "${Utils.convertNumber(dayOfMonth)} ${Utils.persianMonth(month)}"
    }
}

enum class MonthModel {
Previous,Current,Post
}
