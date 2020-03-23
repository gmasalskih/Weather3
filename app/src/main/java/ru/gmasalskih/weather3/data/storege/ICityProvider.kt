package ru.gmasalskih.weather3.data.storege

import ru.gmasalskih.weather3.data.City
import java.util.*

interface ICityProvider {
    fun getCity(name: String): City?
    fun getCity(lat: Double, lon: Double): City?
    fun getCity(uuid: UUID): City?
    fun addCity(city: City): Boolean
    fun removeCity(city: City): Boolean
}