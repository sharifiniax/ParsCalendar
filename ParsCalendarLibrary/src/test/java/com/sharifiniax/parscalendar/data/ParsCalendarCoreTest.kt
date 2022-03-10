package com.sharifiniax.parscalendar.data

import org.junit.Assert.*
import org.junit.Test
import java.util.*

class ParsCalendarCoreTest{

    val parsCalendarCore=com.sharifiniax.parscalendar.data.ParsCalendarCore(GregorianCalendar())
    @Test
    fun lengthMonthTest(){

        val length=parsCalendarCore.iranianMonthLength(12,1403)
        assertEquals(30,length)
//        assertEquals(8,parsCalendarCore.iranianDay)
//        assertEquals(12,parsCalendarCore.iranianMonth)
//


    }





}