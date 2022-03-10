package com.sharifiniax.parscalendar.data.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "ParsCalendarEvent", strict = false)
class PersiaCalendar{
    @field:ElementList(inline = true)
    lateinit var events: List<Event>
}

@Root(name = "event", strict = false)
class Event {
    @field:Element
    lateinit var calendar: String
    @field:Element
    var day: Int=0
    @field:Element(required = false)
    lateinit var description: Description
    @field:Element(required = false)
    var holiday: Boolean=false
    @field:Element(required = false)
    var month: Int=0
    @field:Element(required = false)
    lateinit var partialKey: String
    @field:Element(required = false)
    lateinit var sources: String
    @field:Element
    lateinit var title: Title
    @field:Element(required = false)
    var year: Int=0
}


@Root(name = "description", strict = false)
class Description{

    @field:Element(name = "fa_IR",required = false)
    lateinit var faIR: String
    @field:Element(name = "en_US",required = false)
    lateinit var enUS: String



}


@Root(name = "title", strict = false)
class Title {

    @field:Element(name = "fa_IR",required = false)
    lateinit var faIR: String
    @field:Element(name = "en_US",required = false)
    lateinit var enUS: String
}