package ru.gmasalskih.weather3.screens.favorite_city

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gmasalskih.weather3.data.entity.City
import ru.gmasalskih.weather3.data.IFavoriteCityProvider
import ru.gmasalskih.weather3.data.providers.FavoriteCityProvider
import ru.gmasalskih.weather3.utils.TAG_LOG
import timber.log.Timber

class FavoriteCityViewModel : ViewModel() {

    private val favoriteCityProvide: IFavoriteCityProvider =
        FavoriteCityProvider
    private var _favoriteCityList = MutableLiveData<List<City>>()
    val favoriteCityList: LiveData<List<City>>
    get() = _favoriteCityList

    init {
        _favoriteCityList.value = FavoriteCityProvider.getFavoriteCities()
    }

    fun onDeleteFavoriteCity(city: City){
        favoriteCityProvide.removeCity(city)
        _favoriteCityList.value = FavoriteCityProvider.getFavoriteCities()
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("$TAG_LOG WeatherViewModel cleared!")
    }
}