package ru.gmasalskih.weather3.screens.location_selection

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.data.storege.internet.GeocoderApi
import ru.gmasalskih.weather3.data.storege.db.LocationsDB
import ru.gmasalskih.weather3.data.storege.db.LocationsDao
import ru.gmasalskih.weather3.data.storege.local.SharedPreferencesProvider
import ru.gmasalskih.weather3.utils.TAG_LOG
import timber.log.Timber

class LocationSelectionViewModel(application: Application) : AndroidViewModel(application) {

    val db: LocationsDao = LocationsDB.getInstance(getApplication()).locationsDao
    private val coroutineScope = CoroutineScope(Dispatchers.IO)


    private val _responseListLocation = MutableLiveData<List<Location>>()
    val responseListLocation: LiveData<List<Location>>
        get() = _responseListLocation

    init {
        Timber.i("$TAG_LOG CitySelectionViewModel created!")
        coroutineScope.launch {
            val locations = db.getAllLocations()
            withContext(Dispatchers.Main) {
                _responseListLocation.value = locations
            }
        }
    }

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
        Timber.i("$TAG_LOG CitySelectionViewModel cleared!")
    }
}