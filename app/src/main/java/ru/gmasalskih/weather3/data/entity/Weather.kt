package ru.gmasalskih.weather3.data.entity

import ru.gmasalskih.weather3.data.entity.City

data class Weather(
    val timestamp: String = "2020-01-01",
    val city: City = City(),
    val temp: Int = 0,
    val windSpeed: Int = 0,
    val windDirection: String = "c",
    val pressure: Int = 0,
    val humidity: Int = 0
)