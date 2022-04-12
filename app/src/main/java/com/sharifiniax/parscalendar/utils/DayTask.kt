package com.sharifiniax.parscalendar.utils

import com.sharifiniax.parscalendar.data.DayModel

sealed class DayTask(){

    object Empty:DayTask()
    data class Day(val day: DayModel):DayTask()

}