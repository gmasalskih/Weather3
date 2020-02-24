package ru.gmasalskih.weather3.data.storege

import ru.gmasalskih.weather3.data.Weather
import java.util.*

interface IWeatherProvider {
    fun getWeather(cityName: String): Weather
    fun getWeather(cityName: String, timestamp: String): Weather
    fun getWeather(cityUUID: UUID): Weather
    fun getWeather(cityUUID: UUID, timestamp: String): Weather
    fun getWeather(lat: Double, lon: Double): Weather
    fun getWeather(lat: Double, lon: Double, timestamp: String): Weather
}