package ru.gmasalskih.weather3.data

import ru.gmasalskih.weather3.data.entity.Weather

interface IWeatherProvider {
    fun addWeather(weather: Weather): Boolean
    fun getAllWeather(): List<Weather>
    fun removeAllWeather(): Unit
}