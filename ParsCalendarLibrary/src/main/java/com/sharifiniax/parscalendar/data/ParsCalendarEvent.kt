package com.sharifiniax.parscalendar.data

import android.content.Context
import com.sharifiniax.parscalendar.R
import com.sharifiniax.parscalendar.data.model.Day
import com.sharifiniax.parscalendar.data.model.Event
import com.sharifiniax.parscalendar.data.model.PersiaCalendar
import com.sharifiniax.parscalendar.data.model.PublicEvent
import org.simpleframework.xml.Serializer
import org.simpleframework.xml.core.Persister
import java.util.*

class ParsCalendarEvent(private val mContext: Context) {


    private val deserializer: Serializer by lazy {
        Persister()
    }

    private val gregorianEvents: List<Event> by lazy {
        val xmlRes = mContext.resources
            .openRawResource(R.raw.gregorian).bufferedReader()
            .use { it.readText() }
        deserializer.read(PersiaCalendar::class.java, xmlRes).events
    }
    private val jalaliEvents: List<Event> by lazy {
        val xmlRes = mContext.resources
            .openRawResource(R.raw.jalali).bufferedReader()
            .use { it.readText() }
        deserializer.read(PersiaCalendar::class.java, xmlRes).events
    }
    private val hijriEvents: List<Event> by lazy {
        val xmlRes = mContext.resources
            .openRawResource(R.raw.hijri).bufferedReader()
            .use { it.readText() }
        deserializer.read(PersiaCalendar::class.java, xmlRes).events
    }

    /**
     * @param day is iranian day type
     * @param month is iranian month type
     * @param year is iranian year type
     *  If Iranian year doesn't pass, it use current Iranian year.
     */
    fun getJalaliEvent(day: Int,
                       month: Int,
                       year: Int=ParsCalendarCore(GregorianCalendar()).iranianYear
    ): List<PublicEvent> {
        val event : MutableList<PublicEvent> = ArrayList()

        jalaliEvents.filter {

            it.month == month
        }.filter {

            it.day == day
        }.forEach {

            val pE=PublicEvent(day,month,year
                ,it.holiday,it.title,it.description)
            event.add(pE)
        }
        return event
    }

    /**
     * @param day is iranian day type
     * @param month is iranian month type
     * @param year is iranian year type
     * If Iranian year doesn't pass, it use current Iranian year.
     */
    fun getHijriEvent(day: Int,
                      month: Int,
                      year: Int=ParsCalendarCore(GregorianCalendar()).iranianYear
    ): List<PublicEvent> {
        val event : MutableList<PublicEvent> = ArrayList()
        val cal = ParsCalendarCore(GregorianCalendar())
        cal.setIranianDate(year, month, day)

        hijriEvents.filter {
            it.month == cal.arabicMonth
        }.filter {
            it.day == cal.arabicDay
        }.forEach {
            val pE=PublicEvent(day,month,year
                ,it.holiday,it.title,it.description)
            event.add(pE)

        }
        return event
    }

   /**
     * @param day is iranian day type
     * @param month is iranian month type
     * @param year is iranian year type
     * If Iranian year doesn't pass, it use current Iranian year.
    */
    fun getGregorianEvent(day: Int,
                          month: Int,
                          year: Int=ParsCalendarCore(GregorianCalendar()).iranianYear
   ): List<PublicEvent> {
       val event : MutableList<PublicEvent> = ArrayList()
        val cal = ParsCalendarCore(GregorianCalendar())
        cal.setIranianDate(year, month, day)
        gregorianEvents.filter {
            it.day == cal.gregorianDay
        }.filter {
            it.month == cal.gregorianMonth
        }.forEach {

            val pE=PublicEvent(day,month,year
                ,it.holiday,it.title,it.description)
            event.add(pE)
        }
        return event
    }


    fun getGregorianDate(day:Day):Day {
        val cal = ParsCalendarCore(GregorianCalendar())
        cal.setIranianDate(day.year,day.month,day.day)
        return Day(cal.gregorianDay,cal.gregorianMonth,cal.gregorianYear)

    }
    fun getHijriDate(day:Day):Day {
        val cal = ParsCalendarCore(GregorianCalendar())
        cal.setIranianDate(day.year,day.month,day.day)
        return Day(cal.arabicDay,cal.arabicMonth,cal.arabicYear)

    }

    fun getCurrentDate():Day{
        val cal = ParsCalendarCore(GregorianCalendar())
        val day=Day(cal.iranianDay,cal.iranianMonth,cal.iranianYear)
        day.dayOfWeek=cal.iranianDayOfWeek
        return day
    }


}