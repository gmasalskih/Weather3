package ru.gmasalskih.weather3.data.providers

import ru.gmasalskih.weather3.data.entity.Weather
import ru.gmasalskih.weather3.data.IWeatherProvider
import ru.gmasalskih.weather3.data.storege.local.localWeatherStorage

object WeatherProvider : IWeatherProvider {

    override fun addWeather(weather: Weather): Boolean {
        return localWeatherStorage.add(weather)
    }

    override fun getAllWeather(): List<Weather> {
        return localWeatherStorage.toList()
    }

    override fun removeAllWeather() {
        localWeatherStorage.clear()
    }
}