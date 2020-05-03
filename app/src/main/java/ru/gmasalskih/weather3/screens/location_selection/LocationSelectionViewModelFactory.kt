package ru.gmasalskih.weather3.screens.location_selection

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class LocationSelectionViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationSelectionViewModel::class.java)) {
            return LocationSelectionViewModel(application = application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}