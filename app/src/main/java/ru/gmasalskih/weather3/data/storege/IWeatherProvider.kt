package ru.gmasalskih.weather3.data.storege

import ru.gmasalskih.weather3.data.Weather
import java.util.*

interface IWeatherProvider {
    fun getWeather(cityName: String): Weather
    fun getWeather(cityName: String, timestamp: String): Weather
    fun getWeather(lat: Float, lon: Float): Weather
    fun getWeather(lat: Float, lon: Float, timestamp: String): Weather
    fun getAllWeather(): List<Weather>
}