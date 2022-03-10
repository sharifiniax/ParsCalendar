package com.sharifiniax.parscalendar.utils

sealed class MonthState {
    object CurrentMonth : MonthState()
    object PreviousMonth : MonthState()
    object NextMonth : MonthState()
}
