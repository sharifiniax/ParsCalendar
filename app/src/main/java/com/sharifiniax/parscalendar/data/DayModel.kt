package com.sharifiniax.parscalendar.data

import android.icu.util.Calendar
import android.util.Log
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import com.sharifiniax.parscalendar.utils.Utils


data class DayModel(
    var dayOfWeek: Int, val dayOfMonth: Int, val month: Int, val year: Int,

    val monthModel: MonthModel = MonthModel.Current
) {
    override fun toString(): String {
        return "${Utils.convertNumber(dayOfMonth)} ${Utils.persianMonth(month)}"
    }

    fun isAfter(x: DayModel): Boolean {

        val month = if(this.dayOfMonth<10) "${this.month}0".toInt() else this.month
        val year = if(this.month<10) "${this.year}0".toInt() else this.year
        val compareInt = "${year}${month}${this.dayOfMonth}".toInt()
        val xMonth = if(x.dayOfMonth<10) "${x.month}0".toInt() else x.month
        val xYear = if(x.month<10) "${x.year}0".toInt() else x.year
        val xCompareInt = "${xYear}$xMonth${x.dayOfMonth}".toInt()

        return compareInt >= xCompareInt

    }
}

enum class MonthModel {
    Previous, Current, Post
}
