package ru.gmasalskih.weather3.data.storege.local

import ru.gmasalskih.weather3.data.Weather
import ru.gmasalskih.weather3.data.storege.IWeatherProvider
import java.util.*

object LocalWeatherProvider : IWeatherProvider {

    private val storage = localWeatherStorage

    override fun getWeather(cityName: String): Weather =
        storage.first { it.city.name == cityName }

    override fun getWeather(cityName: String, timestamp: String): Weather =
        storage.first { it.city.name == cityName && it.timestamp == timestamp }

    override fun getWeather(cityUUID: UUID): Weather =
        storage.first { it.city.uuid == cityUUID }

    override fun getWeather(cityUUID: UUID, timestamp: String): Weather =
        storage.first { it.city.uuid == cityUUID && it.timestamp == timestamp }

    override fun getWeather(lat: Double, lon: Double): Weather =
        storage.first { it.city.lat == lat && it.city.lon == lon }

    override fun getWeather(lat: Double, lon: Double, timestamp: String): Weather =
        storage.first { it.city.lat == lat && it.city.lon == lon && it.timestamp == timestamp }

    override fun getAllWeather(): List<Weather> {
        return storage
    }
}