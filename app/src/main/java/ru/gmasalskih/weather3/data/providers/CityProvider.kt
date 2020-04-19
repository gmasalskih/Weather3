package ru.gmasalskih.weather3.data.providers

import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.data.ICityProvider
import ru.gmasalskih.weather3.data.storege.local.localCityStorage

object CityProvider : ICityProvider {

    override fun getCity(name: String): List<Location> =
        localCityStorage.filter { location: Location ->
            location.name.trim().toUpperCase() == name.trim().toUpperCase()
        }

    override fun getCity(lat: Float, lon: Float): Location? =
        localCityStorage.firstOrNull { location: Location ->
            location.lat == lat && location.lon == lon
        }

    override fun addCity(location: Location): Boolean =
        localCityStorage.add(location)

    override fun removeCity(location: Location): Boolean =
        localCityStorage.remove(location)

    override fun removeAllCities() {
        localCityStorage.clear()
    }

    override fun getAllCities(): List<Location> {
        return localCityStorage.toList()
    }
}