package ru.gmasalskih.weather3.data.storege.local

import ru.gmasalskih.weather3.data.City
import ru.gmasalskih.weather3.data.storege.ICityProvider
import java.util.*

object LocalCityProvider : ICityProvider {

    private val storage = localCityStorage

    override fun getCity(name: String): City =
        storage.first { it.name == name }

    override fun getCity(lat: Double, lon: Double): City =
        storage.first { it.lat == lat && it.lon == lon }

    override fun getCity(uuid: UUID): City =
        storage.first { it.uuid == uuid }

    override fun addCity(city: City): Boolean =
        storage.add(city)

    override fun removeCity(city: City): Boolean =
        storage.remove(city)
}