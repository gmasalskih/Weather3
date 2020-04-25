package ru.gmasalskih.weather3.data.entity

data class Weather(
    val timestamp: String = "2020-01-01",
    val location: Location = Location(),
    val temp: Int = 0,
    val windSpeed: Int = 0,
    val windDirection: String = "c",
    val pressure: Int = 0,
    val humidity: Int = 0,
    var icon: String = "ovc",
    val url:String = "https://yandex.ru/pogoda/"
)