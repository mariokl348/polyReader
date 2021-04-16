package com.company.epubreader.utils

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {
    private val  REMEMBER_ME = "remember_me"
    private val  USER_NAME = "username"
    private val  PASSWORD = "password"

    private val preferences: SharedPreferences = context.getSharedPreferences("epub_reader",Context.MODE_PRIVATE)

    var rememberMe: Boolean
        get() = preferences.getBoolean(REMEMBER_ME, false)
        set(value) = preferences.edit().putBoolean(REMEMBER_ME, value).apply()

    var username: String
        get() = preferences.getString(USER_NAME, "")!!
        set(value) = preferences.edit().putString(USER_NAME, value).apply()

    var password: String
        get() = preferences.getString(PASSWORD, "")!!
        set(value) = preferences.edit().putString(PASSWORD, value).apply()
}