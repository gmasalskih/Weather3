package ru.gmasalskih.weather3.data.storege.local

import ru.gmasalskih.weather3.data.Weather
import ru.gmasalskih.weather3.data.storege.IWeatherProvider
import ru.gmasalskih.weather3.utils.DATE_PATTERN
import java.text.SimpleDateFormat
import java.util.*

object LocalWeatherProvider : IWeatherProvider {

    private val storage = localWeatherStorage

    override fun getWeather(cityName: String): Weather =
        getWeather(cityName, SimpleDateFormat(DATE_PATTERN).format(Calendar.getInstance().time))

    override fun getWeather(cityName: String, timestamp: String): Weather =
        storage.first { it.city.name == cityName && it.timestamp == timestamp }

    override fun getWeather(lat: Float, lon: Float): Weather =
        storage.first { it.city.lat == lat && it.city.lon == lon }

    override fun getWeather(lat: Float, lon: Float, timestamp: String): Weather =
        storage.first { it.city.lat == lat && it.city.lon == lon && it.timestamp == timestamp }

    override fun getAllWeather(): List<Weather> {
        return storage
    }
}