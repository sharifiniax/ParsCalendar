package com.sharifiniax.parscalendar.di

import android.content.Context
import com.sharifiniax.parscalendar.data.ParsCalendarCore
import com.sharifiniax.parscalendar.data.ParsCalendarEvent
import com.sharifiniax.parscalendar.model.CalendarInteractImpl
import com.sharifiniax.parscalendar.model.CalendarRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ModulesMainFragment {


    @Provides
    @Singleton
    fun getParsCalendar(): ParsCalendarCore {
        return ParsCalendarCore(GregorianCalendar())
    }

    @Provides
    @Singleton
    fun getParsCalendarEvent(@ApplicationContext context: Context): ParsCalendarEvent {
        return ParsCalendarEvent(context)
    }


    @Provides
    @Singleton
    fun getCalendarInteractImpl(calendarCore: ParsCalendarCore,calendarEvent: ParsCalendarEvent): CalendarInteractImpl {
        return CalendarInteractImpl(calendarCore,calendarEvent)
//
    }

    @Provides
    @Singleton
    fun getCalendarRepositoryImpl(calendarInteractImpl: CalendarInteractImpl): CalendarRepositoryImpl {
        return CalendarRepositoryImpl(calendarInteractImpl)
    }

//    @Provides
//    @Singleton
//    fun getCalendarRepositoryImpl(
//
//    ): CalendarRepositoryImpl {
//        return CalendarRepositoryImpl(calendarCore, calendarEvent)
//    }


}


