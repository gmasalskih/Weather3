package ru.gmasalskih.weather3.screens.weather

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.*
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.data.storege.internet.GeocoderApi
import ru.gmasalskih.weather3.data.storege.internet.WeatherApi
import ru.gmasalskih.weather3.data.entity.Weather
import ru.gmasalskih.weather3.data.storege.db.LocationsDB
import ru.gmasalskih.weather3.data.storege.gps.CoordinatesProvider
import ru.gmasalskih.weather3.data.storege.local.SharedPreferencesProvider
import ru.gmasalskih.weather3.utils.DEFAULT_LAT
import ru.gmasalskih.weather3.utils.DEFAULT_LON
import ru.gmasalskih.weather3.utils.EMPTY_COORDINATE
import timber.log.Timber

class WeatherViewModel(
    var lon: String,
    var lat: String,
    application: Application
) : AndroidViewModel(application) {
    private val db by lazy { LocationsDB.getInstance(getApplication()).locationsDao }
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val _currentCoordinate = MutableLiveData<Pair<String, String>>()
    val currentCoordinate: LiveData<Pair<String, String>>
        get() = _currentCoordinate

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

    private val _isCurrentLocationSelected = MutableLiveData<Boolean>()
    val isCurrentLocationSelected: LiveData<Boolean>
        get() = _isCurrentLocationSelected

    init {
        _isLocationSelected.value = false
        _isDateSelected.value = false
        _isLocationWebPageSelected.value = false
    }

    fun initCoordinate(fragment: Fragment) {
        if (isCoordinateEmpty(lat = this.lat, lon = this.lon)) {
            if (isCoordinateEmpty(lat = getLastLat(), lon = getLastLon())) {
                updateCurrentCoordinateFromGPS(fragment)
            } else {
                updateCurrentCoordinateFromSP()
            }
        } else {
            setCurrentCoordinate(lat = this.lat, lon = this.lon)
        }
    }

    fun initLocation(lat: String, lon: String) {
        GeocoderApi.getResponse(lat = lat, lon = lon) { listLocations ->
            listLocations.firstOrNull()?.let { location ->
                var myLocation:Location?
                    coroutineScope.launch {
                    myLocation = db.getLocation(lat = location.lat, lon = location.lon)
                    if (myLocation == null) {
                        db.insert(location)
                        withContext(Dispatchers.Main) {
                            _currentLocation.value = location
                        }
                    } else{
                        withContext(Dispatchers.Main) {
                            _currentLocation.value = myLocation
                        }
                    }
                }
            }
        }
    }

    fun initWeather(location: Location) {
        WeatherApi.getResponse(lat = location.lat, lon = location.lon) { weather: Weather ->
            _currentWeather.value = weather
        }
    }

    private fun getLastLat() = SharedPreferencesProvider.getLastLocationLat(getApplication())
    private fun getLastLon() = SharedPreferencesProvider.getLastLocationLon(getApplication())

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
        currentLocation.value?.let { location ->
            coroutineScope.launch {
                db.updateLocation(location.apply { isFavorite = !isFavorite })
                val myLocation = db.getLocation(lat = location.lat, lon = location.lon)
                withContext(Dispatchers.Main) {
                    _currentLocation.value = myLocation
                }
            }
        }
    }

    private fun updateCurrentCoordinateFromSP() {
        setCurrentCoordinate(
            lat = SharedPreferencesProvider.getLastLocationLat(getApplication()),
            lon = SharedPreferencesProvider.getLastLocationLon(getApplication())
        )
    }

    fun updateCurrentCoordinateFromGPS(fragment: Fragment) {
        CoordinatesProvider.getLastLocation(fragment) { lat: String, lon: String ->
            if (lat == EMPTY_COORDINATE || lon == EMPTY_COORDINATE) {
                setCurrentCoordinate(lat = DEFAULT_LAT, lon = DEFAULT_LON)
            } else {
                setCurrentCoordinate(lat = lat, lon = lon)
            }
            Timber.i(">>>+ $lat $lon")
        }
    }

    private fun isCoordinateEmpty(lat: String, lon: String) =
        lat == EMPTY_COORDINATE || lon == EMPTY_COORDINATE


    private fun setCurrentCoordinate(lat: String, lon: String) {
        _currentCoordinate.value = Pair(lat, lon)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
        coroutineScope.cancel()
    }
}
