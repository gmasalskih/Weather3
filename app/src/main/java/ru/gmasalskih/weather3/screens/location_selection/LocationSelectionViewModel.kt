package ru.gmasalskih.weather3.screens.location_selection
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.data.ILocationProvider
import ru.gmasalskih.weather3.api.GeocoderApi
import ru.gmasalskih.weather3.data.providers.LocationProvider
import ru.gmasalskih.weather3.utils.TAG_LOG
import timber.log.Timber

class LocationSelectionViewModel : ViewModel() {

    private val locationProvider: ILocationProvider = LocationProvider

    private val _responseListLocation = MutableLiveData<List<Location>>()
    val responseListLocation: LiveData<List<Location>>
        get() = _responseListLocation

    init {
        Timber.i("$TAG_LOG CitySelectionViewModel created!")
        _responseListLocation.value = locationProvider.getAllLocations()
    }

    fun sendGeocoderRequest(locationName: String) {
        GeocoderApi.getResponse(locationName){
            _responseListLocation.value = it
        }
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("$TAG_LOG CitySelectionViewModel cleared!")
    }
}