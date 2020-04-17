package ru.gmasalskih.weather3.data.providers

import ru.gmasalskih.weather3.data.entity.City
import ru.gmasalskih.weather3.data.ICityProvider
import ru.gmasalskih.weather3.data.storege.local.localCityStorage

object CityProvider : ICityProvider {

    override fun getCity(name: String): List<City> =
        localCityStorage.filter {city: City ->
            city.name.trim().toUpperCase() == name.trim().toUpperCase()
        }

    override fun getCity(lat: Float, lon: Float): City? =
        localCityStorage.firstOrNull {city: City ->
            city.lat == lat && city.lon == lon
        }

    override fun addCity(city: City): Boolean =
        localCityStorage.add(city)

    override fun removeCity(city: City): Boolean =
        localCityStorage.remove(city)

    override fun removeAllCities() {
        localCityStorage.clear()
    }

    override fun getAllCities(): List<City> {
        return localCityStorage.toList()
    }
}