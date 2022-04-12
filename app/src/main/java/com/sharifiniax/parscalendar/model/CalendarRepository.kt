package com.sharifiniax.parscalendar.model

import com.sharifiniax.parscalendar.data.DayModel

import com.sharifiniax.parscalendar.data.model.PublicEvent
import kotlinx.coroutines.flow.Flow

interface CalendarRepository {


    fun currentMonth():List<DayModel?>

    fun previousMonth():List<DayModel?>

    fun nextMonth():List<DayModel?>

    fun today():DayModel

    fun jalaliEvent(day:DayModel):List<PublicEvent>
    fun hijriEvent(dayModel: DayModel): List<PublicEvent>
    fun gregorianEvent(dayModel: DayModel): List<PublicEvent>
    fun getGregorianDate(dayModel: DayModel): String
    fun getHijriDate(dayModel: DayModel): String
    fun isHoliday(dayModel: DayModel): Boolean

    fun gregorianDateAlphabetic(dayModel: DayModel): String
    fun hijriDateAlphabetic(dayModel: DayModel): String
    fun getToday(): Flow<DayModel>
}