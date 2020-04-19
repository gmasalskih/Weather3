package ru.gmasalskih.weather3.data.providers

import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.data.IFavoriteLocationProvider
import ru.gmasalskih.weather3.data.storege.local.localFavoriteLocationsStorage

object FavoriteLocationProvider :
    IFavoriteLocationProvider {

    private val storage =
        localFavoriteLocationsStorage

    override fun getFavoriteLocations(): List<Location> = storage.sortedBy {
        it.name
    }.toList()

    override fun addLocation(location: Location): Boolean = storage.add(location)

    override fun removeLocation(location: Location): Boolean = storage.remove(location)

    override fun isLocationFavorite(location: Location): Boolean = storage.contains(location)

    override fun clearFavoriteLocations() = storage.clear()
}