package ru.gmasalskih.weather3.data.storege.local

import ru.gmasalskih.weather3.data.City
import ru.gmasalskih.weather3.data.storege.ICityProvider
import java.util.*

object LocalCityProvider : ICityProvider {

    private val storage = localCityStorage

    override fun getCity(name: String): City? =
        storage.firstOrNull { it.name == name }

    override fun getCity(lat: Float, lon: Float): City? =
        storage.firstOrNull { it.lat == lat && it.lon == lon }

    override fun addCity(city: City): Boolean =
        storage.add(city)

    override fun removeCity(city: City): Boolean =
        storage.remove(city)
}