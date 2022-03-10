package com.sharifiniax.parscalendar.ui.main

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.sharifiniax.parscalendar.data.model.PublicEvent
import com.sharifiniax.parscalendar.model.DayModel
import com.sharifiniax.parscalendar.utils.Utils

@BindingAdapter("day")
fun TextView.setDay(dayModel:DayModel?){
    text = dayModel?.dayOfMonth?.let{ Utils.convertNumber(it)} ?: " "

}
@BindingAdapter("app:day_dialog")
fun TextView.setDayDialog(dayModel:DayModel?){
    text = dayModel?.dayOfMonth.toString()

}
@BindingAdapter("app:week_day_dialog")
fun TextView.setWeekDayDialog(dayModel:DayModel?){
    text =
        dayModel?.dayOfWeek?.let {
            getDayWeek(
                it
            )
        }

}

@BindingAdapter("title")
fun TextView.setTitle(event: PublicEvent){
    text= event.titles.faIR

}
fun getDayWeek(day:Int):String{


    return when(day){

        0->{
            "Saturday"
        }
        1->{
            "Sunday"
        }
        2->{
            "Monday"
        }
        3->{
            "Tuesday"
        }
        4->{
            "Wednesday"
        }
        5->{
            "Thursday"
        }
        6->{
            "Friday"
        }

        else -> {
            throw IllegalArgumentException()
        }
    }

}
