package ru.gmasalskih.weather3.screens.favorite_city

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.data.IFavoriteCityProvider
import ru.gmasalskih.weather3.data.providers.FavoriteCityProvider
import ru.gmasalskih.weather3.utils.TAG_LOG
import timber.log.Timber

class FavoriteCityViewModel : ViewModel() {

    private val favoriteCityProvide: IFavoriteCityProvider =
        FavoriteCityProvider
    private var _favoriteCityList = MutableLiveData<List<Location>>()
    val favoriteLocationList: LiveData<List<Location>>
    get() = _favoriteCityList

    init {
        _favoriteCityList.value = FavoriteCityProvider.getFavoriteCities()
    }

    fun onDeleteFavoriteCity(location: Location){
        favoriteCityProvide.removeCity(location)
        _favoriteCityList.value = FavoriteCityProvider.getFavoriteCities()
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("$TAG_LOG WeatherViewModel cleared!")
    }
}