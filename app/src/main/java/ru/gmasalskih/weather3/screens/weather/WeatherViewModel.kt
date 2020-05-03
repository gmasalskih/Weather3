package ru.gmasalskih.weather3.screens.weather

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.*
import ru.gmasalskih.weather3.api.GeocoderApi
import ru.gmasalskih.weather3.api.WeatherApi
import ru.gmasalskih.weather3.data.ILocationProvider
import ru.gmasalskih.weather3.data.IFavoriteLocationProvider
import ru.gmasalskih.weather3.data.IWeatherProvider
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.data.entity.Weather
import ru.gmasalskih.weather3.data.providers.LocationProvider
import ru.gmasalskih.weather3.data.providers.FavoriteLocationProvider
import ru.gmasalskih.weather3.data.providers.WeatherProvider
import ru.gmasalskih.weather3.data.storege.db.LocationsDB
import ru.gmasalskih.weather3.utils.TAG_LOG
import timber.log.Timber

class WeatherViewModel(
    var lon: Float,
    var lat: Float,
    application: Application
) : AndroidViewModel(application) {
    private val weatherProvider: IWeatherProvider = WeatherProvider
    private val favoriteLocationProvider: IFavoriteLocationProvider = FavoriteLocationProvider
    private val locationProvider: ILocationProvider = LocationProvider
    val db by lazy { LocationsDB.getInstance(getApplication()).locationsDao }
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val _currentLocation = MutableLiveData<Location>()
    val currentLocation: LiveData<Location>
        get() = _currentLocation

    private val _currentWeather = MutableLiveData<Weather>()
    val currentWeather: LiveData<Weather>
        get() = _currentWeather

    private val _isLocationSelected = MutableLiveData<Boolean>()
    val isLocationSelected: LiveData<Boolean>
        get() = _isLocationSelected

    private val _isLocationWebPageSelected = MutableLiveData<Boolean>()
    val isLocationWebPageSelected: LiveData<Boolean>
        get() = _isLocationWebPageSelected

    private val _isDateSelected = MutableLiveData<Boolean>()
    val isDateSelected: LiveData<Boolean>
        get() = _isDateSelected

    private val _isLocationFavoriteSelected = MutableLiveData<Boolean>()
    val isLocationFavoriteSelected: LiveData<Boolean>
        get() = _isLocationFavoriteSelected

    init {
        initLocation();
        updateFavoriteLocationStatus()
        _isLocationSelected.value = false
        _isDateSelected.value = false
        _isLocationFavoriteSelected.value = false
        _isLocationWebPageSelected.value = false
        Timber.i("$TAG_LOG WeatherViewModel created!")
    }

    private fun initLocation() {
        coroutineScope.launch {
            val locations = db.getLocation(lat = lat, lon = lon)
            if (locations.isNullOrEmpty()) {
                GeocoderApi.getResponse("$lon,$lat") { listLocations ->
                    listLocations.first().let { location ->
                        coroutineScope.launch {
                            db.insert(location)
                            setLocation(location)
                        }
                    }
                }
            } else {
                setLocation(locations.first())
            }
        }
    }

    private suspend fun setLocation(location: Location) {
        withContext(Dispatchers.Main) {
            _currentLocation.value = location
            sendWeatherRequest()
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

    fun updateFavoriteLocationStatus() {
        _currentLocation.value?.let { location: Location ->
            _isLocationFavoriteSelected.value =
                favoriteLocationProvider.isLocationFavorite(location)
        }
    }

    // Click Event
    fun onLocationSelect() {
        _isLocationSelected.value = true
        _isLocationSelected.value = false
    }

    fun onLocationWebPageSelect() {
        _isLocationWebPageSelected.value = true
        _isLocationWebPageSelected.value = false
    }

    fun onDateSelect() {
        _isDateSelected.value = true
        _isDateSelected.value = false
    }

    fun onToggleFavoriteLocation() {
        _isLocationFavoriteSelected.value?.let { event: Boolean ->
            _currentLocation.value?.let { location: Location ->
                if (event) {
                    favoriteLocationProvider.removeLocation(location)
                } else {
                    favoriteLocationProvider.addLocation(location)
                }
                updateFavoriteLocationStatus()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
        Timber.i("$TAG_LOG WeatherViewModel cleared!")
    }
}