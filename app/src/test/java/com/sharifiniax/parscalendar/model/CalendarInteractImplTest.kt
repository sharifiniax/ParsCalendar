package com.sharifiniax.parscalendar.model

import com.sharifiniax.parscalendar.data.ParsCalendarCore
import com.sharifiniax.parscalendar.utils.MonthState
import org.junit.Assert.*
import org.junit.Test
import java.util.*


class CalendarInteractImplTest {


    private val calendar = ParsCalendarCore(GregorianCalendar())

    val calendarInteractImpl = CalendarInteractImpl(calendar)

    @Test
    fun getListMonth(){

    val list_1=calendarInteractImpl.getListOfMonth(MonthState.CurrentMonth)
    val list_2=calendarInteractImpl.getListOfMonth(MonthState.NextMonth)
    val list_3=calendarInteractImpl.getListOfMonth(MonthState.NextMonth)
    val list_4=calendarInteractImpl.getListOfMonth(MonthState.NextMonth)

    val sas=false
    

    }


}