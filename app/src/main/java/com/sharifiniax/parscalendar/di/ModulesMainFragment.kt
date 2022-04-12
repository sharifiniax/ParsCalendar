package com.sharifiniax.parscalendar.di

import android.content.Context
import com.sharifiniax.parscalendar.data.ParsCalendarCore
import com.sharifiniax.parscalendar.data.ParsCalendarEvent
import com.sharifiniax.parscalendar.model.CalendarInteractImpl
import com.sharifiniax.parscalendar.model.CalendarRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton


@Module
@InstallIn(ViewModelComponent::class)
class ModulesMainFragment {


    @Provides

    fun getParsCalendar(): ParsCalendarCore {
        return ParsCalendarCore(GregorianCalendar())
    }

    @Provides

    fun getParsCalendarEvent(@ApplicationContext context: Context): ParsCalendarEvent {
        return ParsCalendarEvent(context)
    }


    @Provides

    fun getCalendarInteractImpl(calendarCore: ParsCalendarCore,calendarEvent: ParsCalendarEvent): CalendarInteractImpl {
        return CalendarInteractImpl(calendarCore,calendarEvent)

    }

    @Provides

    fun getCalendarRepositoryImpl(calendarInteractImpl: CalendarInteractImpl): CalendarRepositoryImpl {
        return CalendarRepositoryImpl(calendarInteractImpl)
    }



}


