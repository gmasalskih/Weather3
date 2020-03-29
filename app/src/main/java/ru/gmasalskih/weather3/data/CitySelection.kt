package ru.gmasalskih.weather3.data

data class CitySelection(
    val name: String? = "",
    val addressLine: String? = "",
    val countryName: String? = "",
    val countyCode: String? = "",
    val lat: Double? = 0.0,
    val lon: Double? = 0.0,
    val url: String = "https://yandex.ru/pogoda/$name"
)

