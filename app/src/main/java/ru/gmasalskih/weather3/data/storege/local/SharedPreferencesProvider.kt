package ru.gmasalskih.weather3.data.storege.local

import android.app.Application
import android.content.Context
import ru.gmasalskih.weather3.utils.EMPTY_COORDINATE

object SharedPreferencesProvider {
    private const val APP_PREFERENCES = "appPreferences"
    private const val APP_PREFERENCES_LAST_LAT = "lastLocationLat"
    private const val APP_PREFERENCES_LAST_LON = "lastLocationLon"

    fun setLastLocationCoordinates(lat: String, lon: String, application: Application) {
        application.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
            .edit()
            .putString(APP_PREFERENCES_LAST_LAT, lat)
            .putString(APP_PREFERENCES_LAST_LON, lon)
            .apply()
    }

    fun getLastLocationLat(application: Application): String {
        return application.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
            .getString(APP_PREFERENCES_LAST_LAT, EMPTY_COORDINATE)!!
    }

    fun getLastLocationLon(application: Application): String {
        return application.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
            .getString(APP_PREFERENCES_LAST_LON, EMPTY_COORDINATE)!!
    }
}