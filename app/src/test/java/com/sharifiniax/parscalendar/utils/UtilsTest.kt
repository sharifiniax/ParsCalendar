package com.sharifiniax.parscalendar.utils

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

import org.junit.Before
import org.junit.Test
import java.lang.reflect.Type

class UtilsTest{


    private lateinit var aban: String

    @Test
    fun getListEvent() {

        val gson = Gson()
        val listEventType: Type = object : TypeToken<Array<Event?>?>() {}.type
        val list:List<Event> = gson.fromJson(aban, listEventType )

        Log.d("abanJson",list.size.toString())

    }


    @Before
    fun initialize() {
        Logger.addLogAdapter(AndroidLogAdapter())

        aban = "{\n" +
                "      \"partial_key\": \"statistics_and_planning_day\",\n" +
                "      \"title\": \"روز آمار و برنامه\u200Cریزی\",\n" +
                "      \"description\": \"روز آمار و برنامه\u200Cریزی\",\n" +
                "      \"month\": 8,\n" +
                "      \"day\": 1,\n" +
                "      \"calendar\": \"Iran Official\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"partial_key\": \"capitulation_in_iran\",\n" +
                "      \"title\": \"اعتراض و افشاگری امام خمينی عليه پذيرش كاپيتولاسيون\",\n" +
                "      \"description\": \"اعتراض و افشاگری امام خمينی عليه پذيرش كاپيتولاسيون\",\n" +
                "      \"year\": 1343,\n" +
                "      \"month\": 8,\n" +
                "      \"day\": 4,\n" +
                "      \"calendar\": \"Iran Official\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"partial_key\": \"cyrus_the_great_day\",\n" +
                "      \"title\": \"روز کوروش بزرگ\",\n" +
                "      \"description\": \"روز کوروش بزرگ\",\n" +
                "      \"month\": 8,\n" +
                "      \"day\": 7,\n" +
                "      \"calendar\": \"Ancient Iran\"\n" +
                "    }"

    }












}