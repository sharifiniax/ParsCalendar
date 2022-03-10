package com.sharifiniax.parscalendar.model

import com.sharifiniax.parscalendar.data.model.PublicEvent
import com.sharifiniax.parscalendar.utils.MonthState
import javax.inject.Inject


class CalendarRepositoryImpl @Inject constructor(
    private val calendarInteract: CalendarInteractImpl
    ):CalendarRepository  {



    override fun currentMonth(): List<DayModel> {
        return calendarInteract.getListOfMonth(MonthState.CurrentMonth)
    }

    override fun previousMonth(): List<DayModel> {
        return calendarInteract.getListOfMonth(MonthState.PreviousMonth)
    }

    override fun nextMonth(): List<DayModel> {
        return calendarInteract.getListOfMonth(MonthState.NextMonth)
    }

    override fun today(): DayModel {
        return calendarInteract.today()
    }

    override fun jalaliEvent(day: DayModel):List<PublicEvent> {
        return calendarInteract.jalaliEvent(day)
    }

    override fun hijriEvent(dayModel: DayModel): List<PublicEvent> {
        return calendarInteract.hijriEvent(dayModel)
    }

    override fun gregorianEvent(dayModel: DayModel): List<PublicEvent> {
        return calendarInteract.gregorianEvent(dayModel)
    }

    override fun getGregorianDate(dayModel: DayModel): String {
        return calendarInteract.gregorianDate(dayModel)
    }

    override fun getHijriDate(dayModel: DayModel): String {
        return calendarInteract.hijriDate(dayModel)
    }

    override fun isHoliday(dayModel: DayModel): Boolean {
        return calendarInteract.isHoliday(dayModel)
    }

    override fun gregorianDateAlphabetic(dayModel: DayModel): String {
        return calendarInteract.gregorianDateAlphabetic(dayModel)
    }

    override fun hijriDateAlphabetic(dayModel: DayModel): String {
        return calendarInteract.hijriDateAlphabetic(dayModel)
    }


}
