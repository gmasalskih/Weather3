package ru.gmasalskih.weather3.screens.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gmasalskih.weather3.api.GeocoderApi
import ru.gmasalskih.weather3.api.WeatherApi
import ru.gmasalskih.weather3.data.ICityProvider
import ru.gmasalskih.weather3.data.IFavoriteCityProvider
import ru.gmasalskih.weather3.data.IWeatherProvider
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.data.entity.Weather
import ru.gmasalskih.weather3.data.providers.CityProvider
import ru.gmasalskih.weather3.data.providers.FavoriteCityProvider
import ru.gmasalskih.weather3.data.providers.WeatherProvider
import ru.gmasalskih.weather3.utils.TAG_LOG
import timber.log.Timber

class WeatherViewModel(
    var lon: Float,
    var lat: Float
) : ViewModel() {
    private val weatherProvider: IWeatherProvider = WeatherProvider
    private val favoriteCityProvider: IFavoriteCityProvider = FavoriteCityProvider
    private val cityProvider: ICityProvider = CityProvider

    private val _currentLocation = MutableLiveData<Location>()
    val currentLocation: LiveData<Location>
        get() = _currentLocation

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
        initCity();
        updateFavoriteCityStatus()
        _isCitySelected.value = false
        _isDateSelected.value = false
        _isCityFavoriteSelected.value = false
        _isCityWebPageSelected.value = false
        Timber.i("$TAG_LOG WeatherViewModel created!")
    }

    private fun initCity() {
        if (cityProvider.getCity(lat = lat, lon = lon) == null) {
            GeocoderApi.getResponse("$lon,$lat") {
                cityProvider.addCity(it.first())
                _currentLocation.value = it.first()
                sendWeatherRequest()
            }
        }
    }

    private fun sendWeatherRequest() {
        _currentLocation.value?.let { location: Location ->
            WeatherApi.getResponse(location) { weather: Weather ->
                _currentWeather.value = weather
                weatherProvider.addWeather(weather)
            }
        }

    }

    fun updateFavoriteCityStatus() {
        _currentLocation.value?.let { location: Location ->
            _isCityFavoriteSelected.value = favoriteCityProvider.isCityFavorite(location)
        }
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
            _currentLocation.value?.let { location: Location ->
                if (event) {
                    favoriteCityProvider.removeCity(location)
                } else {
                    favoriteCityProvider.addCity(location)
                }
                updateFavoriteCityStatus()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("$TAG_LOG WeatherViewModel cleared!")
    }
}