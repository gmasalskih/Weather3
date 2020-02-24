package ru.gmasalskih.weather3.data.storege

import ru.gmasalskih.weather3.data.City

interface IFavoriteCityProvider {
    fun getFavoriteCities(): List<City>
    fun addCity(city: City): Boolean
    fun delCity(city: City) : Boolean
}
