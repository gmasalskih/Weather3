package ru.gmasalskih.weather3.screens.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gmasalskih.weather3.data.Weather
import ru.gmasalskih.weather3.data.storege.IWeatherProvider
import ru.gmasalskih.weather3.data.storege.local.LocalWeatherProvider
import timber.log.Timber

class WeatherViewModel(cityName: String, timestamp: String) : ViewModel() {

    private val weatherProvider: IWeatherProvider = LocalWeatherProvider

    private val _currentWeather = MutableLiveData<Weather>()
    val currentWeather: LiveData<Weather>
        get() = _currentWeather

    private val _isCitySelected = MutableLiveData<Boolean>()
    val isCitySelected: LiveData<Boolean>
        get() = _isCitySelected

    private val _isCityWebPageSelected = MutableLiveData<Boolean>()
    val isCityWebPageSelected: LiveData<Boolean>
        get() = _isCityWebPageSelected

    private val _isDateSelected = MutableLiveData<Boolean>()
    val isDateSelected: LiveData<Boolean>
        get() = _isDateSelected

    private val _isCityFavoriteSelected = MutableLiveData<Boolean>()
    val isCityFavoriteSelected: LiveData<Boolean>
        get() = _isCityFavoriteSelected


    init {
        Timber.i("--- WeatherViewModel created!")
        if(timestamp == "2020-01-01"){
            _currentWeather.value = weatherProvider.getWeather(cityName)
        } else {
            _currentWeather.value = weatherProvider.getWeather(cityName, timestamp)
        }


        _isCitySelected.value = false
        _isDateSelected.value = false
        _isCityWebPageSelected.value = false
    }

    // Click Event
    fun onCitySelect() {
        _isCitySelected.value = true
        _isCitySelected.value = false
    }

    fun onCityWebPageSelect() {
        _isCityWebPageSelected.value = true
        _isCityWebPageSelected.value = false
    }

    fun onDateSelect() {
        _isDateSelected.value = true
        _isDateSelected.value = false
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("--- WeatherViewModel cleared!")
    }
}