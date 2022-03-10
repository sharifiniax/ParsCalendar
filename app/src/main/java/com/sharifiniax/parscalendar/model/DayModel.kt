package com.sharifiniax.parscalendar.model

import java.math.BigInteger
import java.security.MessageDigest

data class DayModel(var dayOfWeek: Int, val dayOfMonth: Int, val month:Int, val year:Int,
                    val monthModel:MonthModel){

    val id = md5(year.toString()+month+""+dayOfMonth+""+monthModel)

   companion object {
       fun md5(input: String): String {
           val md = MessageDigest.getInstance("MD5")
           return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
       }
   }

}

enum class MonthModel {
Previous,Current,Post
}
