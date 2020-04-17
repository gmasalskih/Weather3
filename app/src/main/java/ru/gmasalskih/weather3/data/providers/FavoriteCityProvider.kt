package ru.gmasalskih.weather3.data.providers

import ru.gmasalskih.weather3.data.entity.City
import ru.gmasalskih.weather3.data.IFavoriteCityProvider
import ru.gmasalskih.weather3.data.storege.local.localFavoriteCityStorage

object FavoriteCityProvider :
    IFavoriteCityProvider {

    private val storage =
        localFavoriteCityStorage

    override fun getFavoriteCities(): List<City> = storage.sortedBy {
        it.name
    }.toList()

    override fun addCity(city: City): Boolean = storage.add(city)

    override fun removeCity(city: City): Boolean = storage.remove(city)

    override fun isCityFavorite(city: City): Boolean = storage.contains(city)

    override fun clearFavoriteCities() = storage.clear()
}