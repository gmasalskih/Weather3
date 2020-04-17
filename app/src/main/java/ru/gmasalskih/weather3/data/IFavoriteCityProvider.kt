package ru.gmasalskih.weather3.data

import ru.gmasalskih.weather3.data.entity.City

interface IFavoriteCityProvider {
    fun getFavoriteCities(): List<City>
    fun addCity(city: City): Boolean
    fun removeCity(city: City) : Boolean
    fun isCityFavorite(city: City): Boolean
    fun clearFavoriteCities(): Unit
}
