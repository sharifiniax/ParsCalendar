package com.sharifiniax.parscalendar.model

import com.sharifiniax.parscalendar.data.ParsCalendarCore
import com.sharifiniax.parscalendar.data.ParsCalendarEvent
import com.sharifiniax.parscalendar.data.model.Event
import com.sharifiniax.parscalendar.data.model.PublicEvent
import com.sharifiniax.parscalendar.utils.MonthState

interface CalendarInteract {

    abstract val calendarEvent:ParsCalendarEvent
    abstract val calendar:ParsCalendarCore
    fun getListOfMonth(state:MonthState):List<DayModel?>
    fun today():DayModel
    fun jalaliEvent(day:DayModel):List<PublicEvent>


    fun hijriEvent(day: DayModel): List<PublicEvent>
    fun gregorianEvent(day: DayModel): List<PublicEvent>
    fun gregorianDate(dayModel: DayModel): String
    fun hijriDate(dayModel: DayModel): String
    fun gregorianDateAlphabetic(dayModel: DayModel): String
    fun hijriDateAlphabetic(dayModel: DayModel): String

    fun isHoliday(dayModel: DayModel): Boolean

}