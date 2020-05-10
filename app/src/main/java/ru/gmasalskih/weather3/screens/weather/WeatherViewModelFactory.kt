package ru.gmasalskih.weather3.screens.weather

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.gmasalskih.weather3.utils.toCoordinate
import java.lang.IllegalArgumentException

class WeatherViewModelFactory(
    private val lon: String,
    private val lat: String,
    private val application: Application
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(
                lon = lon.toCoordinate(),
                lat = lat.toCoordinate(),
                application = application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}