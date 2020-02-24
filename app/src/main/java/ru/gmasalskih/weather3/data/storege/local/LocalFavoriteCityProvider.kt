package ru.gmasalskih.weather3.data.storege.local

import ru.gmasalskih.weather3.data.City
import ru.gmasalskih.weather3.data.storege.IFavoriteCityProvider

object LocalFavoriteCityProvider : IFavoriteCityProvider {

    private val storage = localFavoriteCityStorage

    override fun getFavoriteCities(): List<City> = storage.toList()

    override fun addCity(city: City): Boolean = storage.add(city)

    override fun delCity(city: City): Boolean = storage.remove(city)
}