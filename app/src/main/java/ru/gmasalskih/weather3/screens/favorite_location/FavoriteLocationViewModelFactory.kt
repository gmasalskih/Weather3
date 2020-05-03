package ru.gmasalskih.weather3.screens.favorite_location

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class FavoriteLocationViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteLocationViewModel::class.java)) {
            return FavoriteLocationViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}