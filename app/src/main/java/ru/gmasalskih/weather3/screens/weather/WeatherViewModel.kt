package ru.gmasalskih.weather3.screens.weather

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.*
import ru.gmasalskih.weather3.data.storege.internet.GeocoderApi
import ru.gmasalskih.weather3.data.storege.internet.WeatherApi
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.data.entity.Weather
import ru.gmasalskih.weather3.data.storege.db.LocationsDB
import ru.gmasalskih.weather3.data.storege.gps.CoordinatesProvider
import ru.gmasalskih.weather3.data.storege.local.SharedPreferencesProvider
import ru.gmasalskih.weather3.utils.TAG_LOG
import ru.gmasalskih.weather3.utils.toast
import timber.log.Timber

class WeatherViewModel(
    var lon: String,
    var lat: String,
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

    private val _isCurrentLocationSelected = MutableLiveData<Boolean>()
    val isCurrentLocationSelected: LiveData<Boolean>
        get() = _isCurrentLocationSelected

    init {
        _isLocationSelected.value = false
        _isDateSelected.value = false
        _isLocationFavoriteSelected.value = false
        _isLocationWebPageSelected.value = false
        sendWeatherRequest()
    }

    fun initCoordinates(fragment: Fragment) {
        Timber.i("GPS--- initCoordinates1")
        CoordinatesProvider.getLastLocation(fragment) { lat: String, lon: String ->
            GeocoderApi.getResponse(lat = lat, lon = lon) { listLocations ->
                listLocations.first().let { location ->
                    Timber.i("GPS--- initCoordinates2 $location")
                    this.lat = location.lat
                    this.lon = location.lon
                    setLastSelectedCoordinate(lat = location.lat, lon = location.lon)
                    initLocation()
                }
            }
        }
    }

    fun setLastSelectedCoordinate(lat: String, lon: String) {
        SharedPreferencesProvider.setLastLocationCoordinates(
            lat = lat,
            lon = lon,
            application = getApplication()
        )
    }

    fun initLocation() {
        coroutineScope.launch {
            Timber.i(
                "GPS--- initLocation lat:$lat  lon:$lon ${db.getLocation(
                    lat = lat,
                    lon = lon
                )}"
            )
            if (db.getLocation(lat = lat, lon = lon).isNullOrEmpty()) {
                GeocoderApi.getResponse(lat = lat, lon = lon) { listLocations ->
                    listLocations.firstOrNull()?.let { location ->
                        Timber.i("GPS--- initLocation if $location")
                        lat = location.lat
                        lon = location.lon
                        coroutineScope.launch {
                            db.insert(location)
                            updateCurrentLocation()
                        }
                    }
                }
            } else {
                Timber.i("GPS--- initLocation else")
                updateCurrentLocation()
            }
        }
    }

    private suspend fun updateCurrentLocation() {
        coroutineScope.launch {
            Timber.i("GPS--- updateCurrentLocation: lat:$lat lon:$lon")
            val location = db.getLocation(lat = lat, lon = lon).firstOrNull()
            Timber.i("GPS--- updateCurrentLocation: $location")
            if (location != null) {
                withContext(Dispatchers.Main) {
                    _currentLocation.value = location
                    _isLocationFavoriteSelected.value = location.isFavorite
                    sendWeatherRequest()
                }
            }

        }
    }

    fun getLastLocationLat() = SharedPreferencesProvider.getLastLocationLat(getApplication())
    fun getLastLocationLon() = SharedPreferencesProvider.getLastLocationLon(getApplication())

    fun showLastSelectedLocationCoordinates() {
        val _lat = SharedPreferencesProvider.getLastLocationLat(getApplication())
        val _lon = SharedPreferencesProvider.getLastLocationLon(getApplication())
        "lat:$_lat lon:$_lon".toast(getApplication())
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

    fun onCurrentLocationSelected() {
        _isCurrentLocationSelected.value = true
        _isCurrentLocationSelected.value = false
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