package ru.gmasalskih.weather3.data

import ru.gmasalskih.weather3.data.entity.Location

interface ICityProvider {
    fun getCity(name: String): List<Location>
    fun getCity(lat: Float, lon: Float): Location?
    fun addCity(location: Location): Boolean
    fun removeCity(location: Location): Boolean
    fun removeAllCities(): Unit
    fun getAllCities(): List<Location>
}