package com.sharifiniax.parscalendar.utils

sealed class PriorityMenuState{

    object Close:PriorityMenuState()
    object Open:PriorityMenuState()

}