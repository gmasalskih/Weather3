package ru.gmasalskih.weather3.screens.location_selection

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.*
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.data.storege.internet.GeocoderApi
import ru.gmasalskih.weather3.data.storege.db.LocationsDB
import ru.gmasalskih.weather3.data.storege.local.SharedPreferencesProvider

class LocationSelectionViewModel(application: Application) : AndroidViewModel(application) {

    private val db by lazy { LocationsDB.getInstance(getApplication()).locationsDao }
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val listLocation = db.getAllLocations()

    private var _responseListLocation = MutableLiveData<List<Location>>()
    val responseListLocation: LiveData<List<Location>>
        get() = _responseListLocation

    fun sendGeocoderRequest(locationName: String) {
        GeocoderApi.getResponse(locationName) {
            _responseListLocation.value = it
        }
    }

    fun addSelectedLocationToDB(location: Location) {
        coroutineScope.launch {
            db.insert(location)
        }
    }

    fun setLastSelectedLocationCoordinates(lat: String, lon: String) {
        SharedPreferencesProvider.setLastLocationCoordinates(
            lat = lat,
            lon = lon,
            application = getApplication()
        )
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}