package com.sharifiniax.parscalendar.utils

sealed class InsertTaskState{

    object Empty:InsertTaskState()
    object Failed:InsertTaskState()
    object Success:InsertTaskState()

}