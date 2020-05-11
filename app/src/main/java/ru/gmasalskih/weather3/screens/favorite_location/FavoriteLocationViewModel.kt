package ru.gmasalskih.weather3.screens.favorite_location

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.data.storege.db.LocationsDB
import ru.gmasalskih.weather3.data.storege.local.SharedPreferencesProvider

class FavoriteLocationViewModel(application: Application) : AndroidViewModel(application) {

    private val db = LocationsDB.getInstance(getApplication()).locationsDao
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var _favoriteLocationList = MutableLiveData<List<Location>>()
    val favoriteLocationList: LiveData<List<Location>>
        get() = _favoriteLocationList

    init {
        coroutineScope.launch {
            setFavoriteLocations(db.getFavoriteLocations())
        }
    }

    fun onDeleteFavoriteCity(location: Location) {
        coroutineScope.launch {
            db.updateLocation(location.apply {
                isFavorite = false
            })
            setFavoriteLocations(db.getFavoriteLocations())
        }
    }

    fun setLastSelectedLocationCoordinates(lat: String, lon: String) {
        SharedPreferencesProvider.setLastLocationCoordinates(
            lat = lat,
            lon = lon,
            application = getApplication()
        )
    }

    private suspend fun setFavoriteLocations(locations: List<Location>) {
        withContext(Dispatchers.Main) {
            _favoriteLocationList.value = locations
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}