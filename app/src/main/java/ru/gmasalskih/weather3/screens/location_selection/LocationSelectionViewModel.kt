package ru.gmasalskih.weather3.screens.location_selection

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.data.ILocationProvider
import ru.gmasalskih.weather3.api.GeocoderApi
import ru.gmasalskih.weather3.data.providers.LocationProvider
import ru.gmasalskih.weather3.data.storege.db.LocationsDB
import ru.gmasalskih.weather3.data.storege.db.LocationsDao
import ru.gmasalskih.weather3.utils.TAG_LOG
import timber.log.Timber

class LocationSelectionViewModel(private val app: Application) : AndroidViewModel(app) {

    private val locationProvider: ILocationProvider = LocationProvider
    val db: LocationsDao = LocationsDB.getInstance(app).locationsDao


    private val _responseListLocation = MutableLiveData<List<Location>>()
    val responseListLocation: LiveData<List<Location>>
        get() = _responseListLocation

    init {
        Timber.i("$TAG_LOG CitySelectionViewModel created!")

//            _responseListLocation.value = db.getAllLocations().value
//        viewModelScope.launch(Dispatchers.IO) {
//        }
//        _responseListLocation.value = locationProvider.getAllLocations()
    }

    fun sendGeocoderRequest(locationName: String) {
        GeocoderApi.getResponse(locationName) {
            _responseListLocation.value = it
        }
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("$TAG_LOG CitySelectionViewModel cleared!")
    }
}