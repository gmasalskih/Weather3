package ru.gmasalskih.weather3.data.storege.local

import ru.gmasalskih.weather3.data.City
import ru.gmasalskih.weather3.data.storege.IFavoriteCityProvider
import java.util.*

object LocalFavoriteCityProvider : IFavoriteCityProvider {

    private val storage = localFavoriteCityStorage

    override fun getFavoriteCities(): List<City> = storage.sortedBy {
        it.name
    }.toList()

    override fun addCity(city: City): Boolean = storage.add(city)

    override fun delCity(city: City): Boolean = storage.remove(city)

    override fun isCityFavorite(city: City): Boolean = storage.contains(city)

    override fun clearFavoriteCities() = storage.clear()
}