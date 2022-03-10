package com.sharifiniax.parscalendar.data.model

import org.simpleframework.xml.Element

data class PublicEvent(
    var day: Int = 0,
    var month: Int = 0,
    var year: Int = 0,
    var holiday: Boolean=false,
    val titles: Title,
    val descriptions: Description

)