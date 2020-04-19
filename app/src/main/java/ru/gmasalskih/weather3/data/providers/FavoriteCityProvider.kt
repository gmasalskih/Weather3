package ru.gmasalskih.weather3.data.providers

import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.data.IFavoriteCityProvider
import ru.gmasalskih.weather3.data.storege.local.localFavoriteCityStorage

object FavoriteCityProvider :
    IFavoriteCityProvider {

    private val storage =
        localFavoriteCityStorage

    override fun getFavoriteCities(): List<Location> = storage.sortedBy {
        it.name
    }.toList()

    override fun addCity(location: Location): Boolean = storage.add(location)

    override fun removeCity(location: Location): Boolean = storage.remove(location)

    override fun isCityFavorite(location: Location): Boolean = storage.contains(location)

    override fun clearFavoriteCities() = storage.clear()
}