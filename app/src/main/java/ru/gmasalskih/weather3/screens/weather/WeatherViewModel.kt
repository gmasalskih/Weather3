package ru.gmasalskih.weather3.screens.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gmasalskih.weather3.data.Weather
import ru.gmasalskih.weather3.data.storege.ICityProvider
import ru.gmasalskih.weather3.data.storege.IFavoriteCityProvider
import ru.gmasalskih.weather3.data.storege.IWeatherProvider
import ru.gmasalskih.weather3.data.storege.local.LocalCityProvider
import ru.gmasalskih.weather3.data.storege.local.LocalFavoriteCityProvider
import ru.gmasalskih.weather3.data.storege.local.LocalWeatherProvider
import timber.log.Timber

class WeatherViewModel(var cityName: String, var timestamp: String) : ViewModel() {

    private val weatherProvider: IWeatherProvider = LocalWeatherProvider
    private val favoriteCityProvider: IFavoriteCityProvider = LocalFavoriteCityProvider
    private val cityProvider: ICityProvider = LocalCityProvider
    private val city = cityProvider.getCity(cityName)

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
        if (timestamp == "2020-01-01") {
            _currentWeather.value = weatherProvider.getWeather(cityName)
        } else {
            _currentWeather.value = weatherProvider.getWeather(cityName, timestamp)
        }
        updateFavoriteCityStatus()
        _isCitySelected.value = false
        _isDateSelected.value = false
        _isCityWebPageSelected.value = false
    }

    fun updateFavoriteCityStatus() {
        _isCityFavoriteSelected.value = favoriteCityProvider.isCityFavorite(city!!)
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

    fun onToggleFavoriteCity() {
        _isCityFavoriteSelected.value?.let { event: Boolean ->
            if (event) {
                favoriteCityProvider.delCity(city!!)
            } else {
                favoriteCityProvider.addCity(city!!)
            }
            updateFavoriteCityStatus()
        }
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("--- WeatherViewModel cleared!")
    }
}