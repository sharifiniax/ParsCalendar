package com.sharifiniax.parscalendar.utils

import android.content.Context

import java.io.IOException
import java.io.InputStream
import kotlin.IllegalArgumentException


class Utils {

    companion object {

        fun convertNumber(num: Int): String {
            val returnValue: StringBuilder = StringBuilder()
            val numToString = num.toString()
            numToString.map {
                if (it.isDigit()) {
                    return@map persianNumber(it)
                } else {
                    return it.toString()
                }

            }.forEach {
                returnValue.append(it)
            }

            return returnValue.toString()
        }


        private fun persianNumber(number: Char): String {
            return when (number) {
                '0' -> {
                    "۰"
                }
                '1' -> {
                    "۱"
                }
                '2' -> {
                    "۲"
                }
                '3' -> {
                    "۳"
                }
                '4' -> {
                    "۴"
                }
                '5' -> {
                    "۵"
                }
                '6' -> {
                    "۶"
                }
                '7' -> {
                    "۷"
                }
                '8' -> {
                    "۸"
                }
                '9' -> {
                    "۹"
                }
                else -> {
                    number.toString()
                }

            }
        }

        fun fenglishMonth(month: Int): String {

            when (month) {

                1 -> {
                    return "Farvardin"
                }
                2 -> {
                    return "Ordibehesht"
                }
                3 -> {
                    return "Khordad"
                }
                4 -> {
                    return "Tir"
                }
                5 -> {
                    return "Mordad"
                }
                6 -> {
                    return "Shahrivar"
                }
                7 -> {
                    return "Mehr"
                }
                8 -> {
                    return "Aban"
                }
                9 -> {
                    return "Azar"
                }
                10 -> {
                    return "Dey"
                }
                11 -> {
                    return "Bahman"
                }
                12 -> {
                    return "Esfand"
                }
                else -> {
                    Throws(IllegalArgumentException::class)
                    return ""
                }

            }
        }

        fun getDayWeek(day: Int): String {


            return when (day) {

                0 -> {
                    "Saturday"
                }
                1 -> {
                    "Sunday"
                }
                2 -> {
                    "Monday"
                }
                3 -> {
                    "Tuesday"
                }
                4 -> {
                    "Wednesday"
                }
                5 -> {
                    "Thursday"
                }
                6 -> {
                    "Friday"
                }

                else -> {
                    throw IllegalArgumentException()
                }
            }

        }

        fun persianMonth(month: Int): String {

            when (month) {

                1 -> {
                    return "فروردین"
                }
                2 -> {
                    return "اردیبهشت"
                }
                3 -> {
                    return "خرداد"
                }
                4 -> {
                    return "تیر"
                }
                5 -> {
                    return "مرداد"
                }
                6 -> {
                    return "شهریور"
                }
                7 -> {
                    return "مهر"
                }
                8 -> {
                    return "آبان"
                }
                9 -> {
                    return "آذر"
                }
                10 -> {
                    return "دی"
                }
                11 -> {
                    return "بهمن"
                }
                12 -> {
                    return "اسفند"
                }
                else -> {
                    Throws(IllegalArgumentException::class)
                    return ""
                }

            }
        }

        fun gregorianMonth(month: Int): String {
            when (month) {

                1 -> {
                    return "ژانویه"
                }
                2 -> {
                    return "فوریه"
                }
                3 -> {
                    return "مارس"
                }
                4 -> {
                    return "آوریل"
                }
                5 -> {
                    return "مه"
                }
                6 -> {
                    return "ژوئن"
                }
                7 -> {
                    return "ژوئیه"
                }
                8 -> {
                    return "اوت"
                }
                9 -> {
                    return "سپتامبر"
                }
                10 -> {
                    return "اکتبر"
                }
                11 -> {
                    return "نوامبر"
                }
                12 -> {
                    return "دسامبر"
                }
                else -> {
                    Throws(IllegalArgumentException::class)
                    return ""
                }

            }
        }

        fun hijriMonth(month: Int): String {
            when (month) {

                1 -> {
                    return "محرم"
                }
                2 -> {
                    return "صفر"
                }
                3 -> {
                    return "ربیع الاول"
                }
                4 -> {
                    return "ربیع الثانی"
                }
                5 -> {
                    return "جمادی الاول"
                }
                6 -> {
                    return "جمادی الثانی"
                }
                7 -> {
                    return "رجب"
                }
                8 -> {
                    return "شعبان"
                }
                9 -> {
                    return "رمضان"
                }
                10 -> {
                    return "شوال"
                }
                11 -> {
                    return "ذی القعده"
                }
                12 -> {
                    return "ذی الحجه"
                }
                else -> {
                    Throws(IllegalArgumentException::class)
                    return ""
                }

            }
        }
    }
}



