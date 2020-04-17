package ru.gmasalskih.weather3.data

import ru.gmasalskih.weather3.data.entity.City

interface ICityProvider {
    fun getCity(name: String): List<City>
    fun getCity(lat: Float, lon: Float): City?
    fun addCity(city: City): Boolean
    fun removeCity(city: City): Boolean
    fun removeAllCities(): Unit
    fun getAllCities(): List<City>
}