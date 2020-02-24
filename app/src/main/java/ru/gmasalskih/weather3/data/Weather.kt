package ru.gmasalskih.weather3.data

import java.util.*

data class Weather(
    val id: UUID = UUID.randomUUID(),
    val timestamp: String = "2020-01-01",
    val city: City = City(),
    val temp: Int = 0,
    val condition: WeatherCondition = WeatherCondition.CLEAR,
    val windSpeed: Int = 0,
    val windDirection: WindDirection = WindDirection.CALM,
    val pressure: Int = 0,
    val humidity: Int = 0,
    val precipitationType: PrecipitationType = PrecipitationType.NO_PRECIPITATION,
    val precipitationStrength: PrecipitationStrength = PrecipitationStrength.NO_PRECIPITATION,
    val cloudiness: Cloudiness = Cloudiness.CLEAR
)