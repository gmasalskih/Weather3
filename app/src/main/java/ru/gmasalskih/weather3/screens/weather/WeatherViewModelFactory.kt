package ru.gmasalskih.weather3.screens.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class WeatherViewModelFactory(
    private val lon: Float,
    private val lat: Float
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(
                lon = lon,
                lat = lat
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}