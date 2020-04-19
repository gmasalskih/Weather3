package ru.gmasalskih.weather3.data

import ru.gmasalskih.weather3.data.entity.Location

interface IFavoriteLocationProvider {
    fun getFavoriteLocations(): List<Location>
    fun addLocation(location: Location): Boolean
    fun removeLocation(location: Location) : Boolean
    fun isLocationFavorite(location: Location): Boolean
    fun clearFavoriteLocations(): Unit
}
