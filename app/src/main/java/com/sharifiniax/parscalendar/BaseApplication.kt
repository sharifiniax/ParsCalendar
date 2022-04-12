package com.sharifiniax.parscalendar

import android.app.Application
import android.content.Context
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication:Application(){

//    companion object{
//        lateinit var app:Context
//    }


    override fun onCreate() {
        super.onCreate()
//        app=baseContext
        Logger.addLogAdapter(AndroidLogAdapter())

    }


}