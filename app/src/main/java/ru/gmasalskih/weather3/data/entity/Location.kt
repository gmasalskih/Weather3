package ru.gmasalskih.weather3.data.entity

data class Location(
    val name: String = "",
    val addressLine: String = "",
    val countryName: String = "",
    val countyCode: String = "",
    val lat: Float = 0.0F,
    val lon: Float = 0.0F
)