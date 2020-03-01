package ru.gmasalskih.weather3.screens.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gmasalskih.weather3.data.Weather
import ru.gmasalskih.weather3.data.storege.IWeatherProvider
import ru.gmasalskih.weather3.data.storege.local.LocalWeatherProvider
import timber.log.Timber


class WeatherViewModel (cityName: String) : ViewModel() {

    private val weatherProvider: IWeatherProvider = LocalWeatherProvider

    private val _currentWeather = MutableLiveData<Weather>()
    val currentWeather: LiveData<Weather>
        get() = _currentWeather


    init {
        Timber.i("--- WeatherViewModel created!")
        _currentWeather.value = weatherProvider.getWeather(cityName)
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("--- WeatherViewModel cleared!")
    }
}