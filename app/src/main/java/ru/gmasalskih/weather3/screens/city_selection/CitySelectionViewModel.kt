package ru.gmasalskih.weather3.screens.city_selection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gmasalskih.weather3.data.storege.ICityProvider
import ru.gmasalskih.weather3.data.storege.local.LocalCityProvider
import timber.log.Timber

class CitySelectionViewModel : ViewModel() {

    private val cityProvider: ICityProvider = LocalCityProvider

    private val _isCityConfirmed = MutableLiveData<Boolean>()
    val isCityConfirmed: LiveData<Boolean>
        get() = _isCityConfirmed

    init {
        Timber.i("--- CitySelectionViewModel created!")
    }

    fun onCityConfirm() {
        _isCityConfirmed.value = true
        _isCityConfirmed.value = false
    }

    fun isEnteredCityValid(cityName: String): Boolean {
        return cityName != ""
    }

    fun hasEnteredCity(cityName: String): Boolean {
        return cityProvider.getCity(cityName) != null
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("--- CitySelectionViewModel cleared!")
    }
}