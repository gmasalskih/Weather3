package ru.gmasalskih.weather3.data

import ru.gmasalskih.weather3.data.entity.Location

interface ILocationProvider {
    fun getLocation(name: String): List<Location>
    fun getLocation(lat: Float, lon: Float): Location?
    fun addLocation(location: Location): Boolean
    fun removeLocation(location: Location): Boolean
    fun removeAllLocations(): Unit
    fun getAllLocations(): List<Location>
}