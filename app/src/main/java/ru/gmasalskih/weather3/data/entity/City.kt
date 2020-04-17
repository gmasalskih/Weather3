package ru.gmasalskih.weather3.data.entity

data class City(
    val name: String = "",
    val addressLine: String = "",
    val countryName: String = "",
    val countyCode: String = "",
    val lat: Float = 0.0F,
    val lon: Float = 0.0F,
    val url: String = "https://yandex.ru/pogoda/$name"
)