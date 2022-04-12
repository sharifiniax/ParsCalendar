package com.sharifiniax.parscalendar

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.sharifiniax.parscalendar.R
import com.sharifiniax.parscalendar.data.ParsCalendarCore
import com.sharifiniax.parscalendar.utils.Utils
import java.util.*

/**
 * Implementation of App Widget functionality.
 */
class CalendarWidget : AppWidgetProvider() {


    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {


        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }


}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    val calendar = ParsCalendarCore(GregorianCalendar())
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.calendar_widget)
    views.setTextViewText(R.id.year, calendar.iranianYear.toString())
    views.setTextViewText(R.id.month, Utils.persianMonth (calendar.iranianMonth))
    views.setTextViewText(R.id.day, calendar.iranianDay.toString())
    appWidgetManager.updateAppWidget(appWidgetId, views)
}