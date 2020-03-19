package ru.gmasalskih.weather3.screens.favorite_city

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gmasalskih.weather3.data.City
import ru.gmasalskih.weather3.data.storege.IFavoriteCityProvider
import ru.gmasalskih.weather3.data.storege.local.LocalFavoriteCityProvider
import timber.log.Timber

class FavoriteCityViewModel : ViewModel() {

    private val favoriteCityProvide: IFavoriteCityProvider = LocalFavoriteCityProvider
    private var _favoriteCityList = MutableLiveData<List<City>>()
    val favoriteCityList: LiveData<List<City>>
    get() = _favoriteCityList

    init {
        _favoriteCityList.value = LocalFavoriteCityProvider.getFavoriteCities()
    }

    fun onDeleteFavoriteCity(city: City){
        favoriteCityProvide.delCity(city)
        _favoriteCityList.value = LocalFavoriteCityProvider.getFavoriteCities()
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("--- WeatherViewModel cleared!")
    }
}