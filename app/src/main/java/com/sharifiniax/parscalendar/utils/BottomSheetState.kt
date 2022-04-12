package com.sharifiniax.parscalendar.utils

sealed class BottomSheetState{

    object Hide:BottomSheetState()
    object Expand:BottomSheetState()
    object Collapse:BottomSheetState()

}
