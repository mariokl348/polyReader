package com.company.epubreader.utils

import android.app.Application

val prefs: Prefs by lazy {
    App.prefs!!
}

class App:Application() {


    override fun onCreate() {
        super.onCreate()
        instance = this
        prefs = Prefs(applicationContext)
    }



    companion object {
        var prefs: Prefs? = null
        lateinit var instance: App
            private set
    }


}