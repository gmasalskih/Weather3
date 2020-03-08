package ru.gmasalskih.weather3.screens.favorite_city

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gmasalskih.weather3.data.City
import ru.gmasalskih.weather3.data.storege.local.LocalFavoriteCityProvider
import timber.log.Timber
import java.util.*

class FavoriteCityViewModel : ViewModel() {

    private var _favoriteCityList = MutableLiveData<List<City>>()
    val favoriteCityList: LiveData<List<City>>
    get() = _favoriteCityList

    init {
        _favoriteCityList.value = LocalFavoriteCityProvider.getFavoriteCities()
    }

    fun onClickFavoriteCity(cityUUID: UUID){
        LocalFavoriteCityProvider.delCity(cityUUID)
        _favoriteCityList.value = LocalFavoriteCityProvider.getFavoriteCities()
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("--- WeatherViewModel cleared!")
    }
}