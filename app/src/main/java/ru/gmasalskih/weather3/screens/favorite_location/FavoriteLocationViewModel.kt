package ru.gmasalskih.weather3.screens.favorite_location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.data.IFavoriteLocationProvider
import ru.gmasalskih.weather3.data.providers.FavoriteLocationProvider
import ru.gmasalskih.weather3.utils.TAG_LOG
import timber.log.Timber

class FavoriteLocationViewModel : ViewModel() {

    private val favoriteLocationProvide: IFavoriteLocationProvider =
        FavoriteLocationProvider
    private var _favoriteCityList = MutableLiveData<List<Location>>()
    val favoriteLocationList: LiveData<List<Location>>
    get() = _favoriteCityList

    init {
        _favoriteCityList.value = FavoriteLocationProvider.getFavoriteLocations()
    }

    fun onDeleteFavoriteCity(location: Location){
        favoriteLocationProvide.removeLocation(location)
        _favoriteCityList.value = FavoriteLocationProvider.getFavoriteLocations()
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("$TAG_LOG WeatherViewModel cleared!")
    }
}