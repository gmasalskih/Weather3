package ru.gmasalskih.weather3.screens.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.gmasalskih.weather3.data.storege.db.LocationsDB

class SettingsViewModel(application: Application) : AndroidViewModel(application) {
    private val db by lazy { LocationsDB.getInstance(getApplication()).locationsDao }
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun onClearHistory() {
        coroutineScope.launch {
            db.clearAllLocations()
        }
    }

    fun onRemoveFavoritesLocations() {
        coroutineScope.launch {
            db.clearAllFavoriteLocations()
        }
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.cancel()
    }
}