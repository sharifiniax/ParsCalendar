package com.sharifiniax.parscalendar.model

import androidx.test.core.app.ActivityScenario.launch
import com.orhanobut.logger.Logger
import com.sharifiniax.parscalendar.MainActivity
import com.sharifiniax.parscalendar.data.DayModel
import com.sharifiniax.parscalendar.data.MonthModel
import com.sharifiniax.parscalendar.data.ParsCalendarCore
import com.sharifiniax.parscalendar.data.ParsCalendarEvent
import com.sharifiniax.parscalendar.data.model.Day
import com.sharifiniax.parscalendar.data.model.PublicEvent
import com.sharifiniax.parscalendar.utils.MonthState
import com.sharifiniax.parscalendar.utils.Utils
import kotlinx.coroutines.*

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ChannelResult
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CalendarInteractImpl @Inject constructor(
    override val calendar: ParsCalendarCore
    ,
    override val calendarEvent: ParsCalendarEvent
) : CalendarInteract {

    override fun getListOfMonth(state: MonthState): List<DayModel> {
        when (state) {
            is MonthState.CurrentMonth -> {
                val list: MutableList<DayModel> = ArrayList()
                goToLastCurrentMonth()
                val endWeekDay=calendar.iranianDayOfWeek
                goToFirstCurrentMonth()
                val startWeekDay = calendar.iranianDayOfWeek

                //first current month

                if (startWeekDay > 0) {
                    goToLastPreviousMonth()
                    val preList=takeLastMonth(calendar.iranianYear,calendar.iranianMonth,startWeekDay)
                    for (element in preList) {
                        list.add(element)
                    }
                }
                //current month
                for (item in 1..getLengthCurrentMonth()) {
                    val day = DayModel(
                        calendar.iranianDayOfWeek,
                        item,
                        calendar.iranianMonth,
                        calendar.iranianYear,
                        MonthModel.Current
                    )
                    list.add(day)
                    calendar.nextDay()
                }
                    //next month
                if (endWeekDay < 6) {
                    val postList=takeFirstMonth(calendar.iranianYear,calendar.iranianMonth,6-endWeekDay)
                    for (element in postList) {
                        list.add(element)
                    }
                }
                goToFirstPreviousMonth()

                return list
            }
            is MonthState.NextMonth -> {
                val list: MutableList<DayModel> = ArrayList()



                goToFirstNextMonth()

                val length = calendar.iranianMonthLength(calendar.iranianMonth, calendar.iranianYear)




                //to get week day of first day of month and last day of month
                val startWeekDay = calendar.iranianDayOfWeek
               goToLastCurrentMonth()
                val endWeekDay=calendar.iranianDayOfWeek
                goToFirstCurrentMonth()



                if (startWeekDay > 0) {
                    calendar.previousDay()
                    val preList=takeLastMonth(calendar.iranianYear,calendar.iranianMonth,startWeekDay)
                    for (element in preList) {
                        list.add(element)
                    }
                }


                for (item in 1..length) {
                    val day = DayModel(
                        calendar.iranianDayOfWeek,
                        item,
                        calendar.iranianMonth,
                        calendar.iranianYear,
                        MonthModel.Current
                    )
                    list.add(day)
                    calendar.nextDay()
                }
                //now is double next month

                if (endWeekDay < 6) {
                    val postList=takeFirstMonth(calendar.iranianYear,calendar.iranianMonth,6-endWeekDay)
                    for (element in postList) {
                        list.add(element)
                    }
                }

                goToFirstPreviousMonth()

                return list
            }
            is MonthState.PreviousMonth -> {
                val list: MutableList<DayModel> = ArrayList()


                goToFirstPreviousMonth()
                val length = calendar.iranianMonthLength(calendar.iranianMonth, calendar.iranianYear)
                val startWeekDay = calendar.iranianDayOfWeek
                goToLastCurrentMonth()
                val endWeekDay=calendar.iranianDayOfWeek
                goToFirstCurrentMonth()


                if (startWeekDay > 0) {
                    calendar.previousDay()
                    val preList=takeLastMonth(calendar.iranianYear,calendar.iranianMonth,startWeekDay)
                    for (element in preList) {
                        list.add(element)
                    }
                }




                for (item in 1..length) {
                    val day = DayModel(
                        calendar.iranianDayOfWeek,
                        item,
                        calendar.iranianMonth,
                        calendar.iranianYear,
                        MonthModel.Current
                    )
                    list.add(day)
                    calendar.nextDay()
                }


                if (endWeekDay < 6) {
                    val postList=takeFirstMonth(calendar.iranianYear,calendar.iranianMonth,6-endWeekDay)
                    for (element in postList) {
                        list.add(element)
                    }
                }

                goToFirstPreviousMonth()

                return list


            }
        }
    }

    private fun takeLastMonth(year:Int, month:Int, num:Int):List<DayModel>{

        val length = calendar.iranianMonthLength(month, year)
        val list: MutableList<DayModel> = ArrayList()
        calendar.setIranianDate(year, month, 1)

        for (item in 1..length) {
            val day = DayModel(
                calendar.iranianDayOfWeek,
                item,
                calendar.iranianMonth,
                calendar.iranianYear,MonthModel.Previous
            )
            list.add(day)
            calendar.nextDay()
        }



        return list.takeLast(num)

    }
    private fun takeFirstMonth(year:Int, month:Int, num:Int):List<DayModel>{

        val length = calendar.iranianMonthLength(month, year)
        val list: MutableList<DayModel> = ArrayList()
        calendar.setIranianDate(year, month, 1)

        for (item in 1..length) {
            val day = DayModel(
                calendar.iranianDayOfWeek,
                item,
                calendar.iranianMonth,
                calendar.iranianYear,
                MonthModel.Post
            )
            list.add(day)
            calendar.nextDay()
        }

        // now is triple next month
       goToFirstPreviousMonth()
        // now is double next month
        return list.take(num)

    }


    private fun goToFirstNextMonth(){
        val length = calendar.iranianMonthLength( calendar.iranianMonth,calendar.iranianYear)
        calendar.setIranianDate(calendar.iranianYear,calendar.iranianMonth,length)
        calendar.nextDay()

    }
    fun goToLastNextMonth(){
        var length = calendar.iranianMonthLength( calendar.iranianMonth,calendar.iranianYear)
        calendar.setIranianDate(calendar.iranianYear,calendar.iranianMonth,length)
        calendar.nextDay()
        length = calendar.iranianMonthLength( calendar.iranianMonth,calendar.iranianYear)
        calendar.setIranianDate(calendar.iranianYear,calendar.iranianMonth,length)

    }
    private fun goToLastPreviousMonth(){

        calendar.setIranianDate(calendar.iranianYear,calendar.iranianMonth,1)
        calendar.previousDay()
        val length = calendar.iranianMonthLength( calendar.iranianMonth,calendar.iranianYear)
        calendar.setIranianDate(calendar.iranianYear,calendar.iranianMonth,length)

    }
    private fun goToFirstPreviousMonth(){
        calendar.setIranianDate(calendar.iranianYear,calendar.iranianMonth,1)
        calendar.previousDay()
        calendar.setIranianDate(calendar.iranianYear,calendar.iranianMonth,1)

    }
    private fun goToFirstCurrentMonth(){
        calendar.setIranianDate(calendar.iranianYear,calendar.iranianMonth,1)
    }
    private fun goToLastCurrentMonth(){
        val length = calendar.iranianMonthLength( calendar.iranianMonth,calendar.iranianYear)
        calendar.setIranianDate(calendar.iranianYear,calendar.iranianMonth,length)
    }
    private fun getLengthCurrentMonth():Int{
        return calendar.iranianMonthLength( calendar.iranianMonth,calendar.iranianYear)

    }
    fun getLengthNextMonth():Int{
        goToFirstNextMonth()
        val length=getLengthCurrentMonth()
        goToFirstPreviousMonth()
        return length

    }
    fun getLengthPreviousMonth():Int{
        goToFirstPreviousMonth()
        val length = getLengthCurrentMonth()
        goToFirstNextMonth()
        return length
    }




    override fun today(): DayModel {

        return DayModel(
            calendar.iranianDayOfWeek,
            calendar.iranianDay,
            calendar.iranianMonth,
            calendar.iranianYear, MonthModel.Current)

    }

    override fun jalaliEvent(day: DayModel): List<PublicEvent> {
        return calendarEvent.getJalaliEvent(
            day.dayOfMonth,
            day.month,
            day.year
            )
    }


  override fun hijriEvent(day: DayModel): List<PublicEvent> {
        return calendarEvent.getHijriEvent(
            day.dayOfMonth,
            day.month,
            day.year
            )
    }


    override fun gregorianEvent(day: DayModel): List<PublicEvent> {
        return calendarEvent.getGregorianEvent(
            day.dayOfMonth,
            day.month,
            day.year
            )
    }

    override fun gregorianDate(it: DayModel): String {


          val day=calendarEvent.getGregorianDate(Day(it.dayOfMonth,it.month,it.year))


        return String.format(
            "میلادی    %s/%s/%s",

            Utils.convertNumber(day.year),
                Utils.convertNumber(day.month),
                    Utils.convertNumber(day.day)
        )


    }

    override fun hijriDate(it: DayModel): String {
        val day=calendarEvent.getHijriDate(Day(it.dayOfMonth,it.month,it.year))


        return String.format(
            "قمری    %s/%s/%s",
            Utils.convertNumber(day.year),
            Utils.convertNumber(day.month),
            Utils.convertNumber(day.day)
        )

    }

    override fun gregorianDateAlphabetic(it: DayModel): String {
        val day=calendarEvent.getGregorianDate(Day(it.dayOfMonth,it.month,it.year))


        return String.format(
            "%s %s",
            Utils.convertNumber(day.day),
            Utils.gregorianMonth(day.month)

        )
    }

    override fun hijriDateAlphabetic(it: DayModel): String {
        val day=calendarEvent.getHijriDate(Day(it.dayOfMonth,it.month,it.year))


        return String.format(
            "%s %s",
            Utils.convertNumber(day.day),
            Utils.hijriMonth(day.month)

            )
    }

    override fun isHoliday(it: DayModel): Boolean {
        var holiday = false
        calendarEvent.getJalaliEvent(it.dayOfMonth,it.month,it.year)
            .forEach { event ->
                if (event.holiday)
                    holiday=true
            }
        calendarEvent.getHijriEvent(it.dayOfMonth,it.month,it.year)
            .forEach { event ->
                if (event.holiday)
                    holiday=true
            }
        calendarEvent.getGregorianEvent(it.dayOfMonth,it.month,it.year)
            .forEach { event ->
                if (event.holiday)
                    holiday=true
            }
        return holiday

    }

    override  fun getToday(): Flow<DayModel> {

        return flow{

            var today = calendarEvent.getCurrentDate()
            Logger.d("first today is emitting now.")
            emit(
                DayModel(
                    calendarEvent.getCurrentDate().dayOfWeek,
                    calendarEvent.getCurrentDate().day,
                    calendarEvent.getCurrentDate().month,
                    calendarEvent.getCurrentDate().year,
                    MonthModel.Current)
            )
            Logger.d("first emit is done.")
            while (true){
                delay(1000)
                if (calendarEvent.getCurrentDate()!=today){
                    today= calendarEvent.getCurrentDate()
                    Logger.d("Second today is emitting now.")
                    emit(
                        DayModel(
                            calendarEvent.getCurrentDate().dayOfWeek,
                            calendarEvent.getCurrentDate().day,
                            calendarEvent.getCurrentDate().month,
                            calendarEvent.getCurrentDate().year,
                            MonthModel.Current)
                    )
                    Logger.d("Second emit is done.")
                }



            }


        }




    }





}