package com.test.news.presentation.internal

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences("AppPreferencesName", Context.MODE_PRIVATE)

    private val LANGUAGE = Pair("LANGUAGE", "en")
    private val COUNTRY_CODE = Pair("COUNTRY_CODE", "US")
    private val CATEGORY = Pair("CATEGORY", "INFOPANE")
    private val OPERATING_SYSTEM = Pair("OPERATING_SYSTEM", "android")
    private val VERSION = Pair("VERSION", "2")

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var language: String?
        get() = preferences.getString(
            LANGUAGE.first,
            LANGUAGE.second
        )
        set(value) = preferences.edit {
            it.putString(LANGUAGE.first, value)
        }

    var countryCode: String?
        get() = preferences.getString(
            COUNTRY_CODE.first,
            COUNTRY_CODE.second
        )
        set(value) = preferences.edit {
            it.putString(COUNTRY_CODE.first, value)
        }

    var category: String?
        get() = preferences.getString(
            CATEGORY.first,
            CATEGORY.second
        )
        set(value) = preferences.edit {
            it.putString(CATEGORY.first, value)
        }

    var operatingSystem: String?
        get() = preferences.getString(
            OPERATING_SYSTEM.first,
            OPERATING_SYSTEM.second
        )
        set(value) = preferences.edit {
            it.putString(OPERATING_SYSTEM.first, value)
        }

    var version: String?
        get() = preferences.getString(
            VERSION.first,
            VERSION.second
        )
        set(value) = preferences.edit {
            it.putString(VERSION.first, value)
        }
}