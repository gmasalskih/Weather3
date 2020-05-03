package ru.gmasalskih.weather3.screens.weather

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.*
import ru.gmasalskih.weather3.api.GeocoderApi
import ru.gmasalskih.weather3.api.WeatherApi
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.data.entity.Weather
import ru.gmasalskih.weather3.data.storege.LocationsDB
import ru.gmasalskih.weather3.utils.TAG_LOG
import timber.log.Timber

class WeatherViewModel(
    var lon: Float,
    var lat: Float,
    application: Application
) : AndroidViewModel(application) {
    private val db by lazy { LocationsDB.getInstance(getApplication()).locationsDao }
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
        _isLocationSelected.value = false
        _isDateSelected.value = false
        _isLocationFavoriteSelected.value = false
        _isLocationWebPageSelected.value = false
        sendWeatherRequest()
        Timber.i("AAA--- WeatherViewModel created!")
    }

    fun initLocation() {
        coroutineScope.launch {
            if (db.getLocation(lat = lat, lon = lon).isNullOrEmpty()) {
                GeocoderApi.getResponse("$lon,$lat") { listLocations ->
                    listLocations.first().let { location ->
                        coroutineScope.launch {
                            db.insert(location)
                            updateCurrentLocation()
                        }
                    }
                }
            } else {
                updateCurrentLocation()
            }
        }
    }

    suspend fun updateCurrentLocation() {
        coroutineScope.launch {
            val location = db.getLocation(lat=lat, lon = lon).first()
            withContext(Dispatchers.Main){
                _currentLocation.value = location
                _isLocationFavoriteSelected.value = location.isFavorite
            }
        }
    }

    private fun sendWeatherRequest() {
        WeatherApi.getResponse(lon = lon, lat = lat) { weather: Weather ->
            _currentWeather.value = weather
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
        coroutineScope.launch {
            val location = db.getLocation(lat = lat, lon = lon).first()
            _isLocationFavoriteSelected.value?.let { event: Boolean ->
                location.isFavorite = !event
                db.updateLocation(location)
                updateCurrentLocation()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
        Timber.i("$TAG_LOG WeatherViewModel cleared!")
    }
}