package ru.gmasalskih.weather3.screens.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class WeatherViewModelFactory(private val cityName: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(cityName) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}