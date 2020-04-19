package ru.gmasalskih.weather3.data

import ru.gmasalskih.weather3.data.entity.Location

interface IFavoriteCityProvider {
    fun getFavoriteCities(): List<Location>
    fun addCity(location: Location): Boolean
    fun removeCity(location: Location) : Boolean
    fun isCityFavorite(location: Location): Boolean
    fun clearFavoriteCities(): Unit
}
