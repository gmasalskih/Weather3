package ru.gmasalskih.weather3.data.storege.local

import android.content.SharedPreferences
import ru.gmasalskih.weather3.data.entity.Coordinates
import ru.gmasalskih.weather3.utils.EMPTY_COORDINATE

class SharedPreferencesProvider(private val sp: SharedPreferences) {

    private val lastLocationLat = "lastLocationLat"
    private val lastLocationLon = "lastLocationLon"

    fun setLastLocationCoordinates(coordinates: Coordinates) {
        sp.edit()
            .putString(lastLocationLat, coordinates.lat)
            .putString(lastLocationLon, coordinates.lon)
            .apply()
    }

    fun getLastLocationCoordinates(): Coordinates{
        val lat = sp.getString(lastLocationLat, EMPTY_COORDINATE) ?: EMPTY_COORDINATE
        val lon = sp.getString(lastLocationLon, EMPTY_COORDINATE) ?: EMPTY_COORDINATE
        return Coordinates(lat = lat, lon = lon)
    }
}