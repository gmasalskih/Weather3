package ru.gmasalskih.weather3.screens.city_selection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gmasalskih.weather3.data.entity.City
import ru.gmasalskih.weather3.data.ICityProvider
import ru.gmasalskih.weather3.api.GeocoderApi
import ru.gmasalskih.weather3.data.providers.CityProvider
import ru.gmasalskih.weather3.utils.TAG_LOG
import timber.log.Timber

class CitySelectionViewModel : ViewModel() {

    private val cityProvider: ICityProvider = CityProvider

    private val _responseListCity = MutableLiveData<List<City>>()
    val responseListCity: LiveData<List<City>>
        get() = _responseListCity

    init {
        Timber.i("$TAG_LOG CitySelectionViewModel created!")
        _responseListCity.value = cityProvider.getAllCities()
    }

    fun sendGeocoderRequest(cityName: String) {
        GeocoderApi.getResponse(cityName){
            _responseListCity.value = it
        }
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("$TAG_LOG CitySelectionViewModel cleared!")
    }
}