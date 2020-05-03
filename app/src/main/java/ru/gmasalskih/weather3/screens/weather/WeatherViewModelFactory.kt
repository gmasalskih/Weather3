package ru.gmasalskih.weather3.screens.weather

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class WeatherViewModelFactory(
    private val lon: Float,
    private val lat: Float,
    private val app: Application
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(
                lon = lon,
                lat = lat,
                application = app
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}