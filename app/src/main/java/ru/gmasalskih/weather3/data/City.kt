package ru.gmasalskih.weather3.data

import java.util.*

data class City(
    val uuid: UUID = UUID.randomUUID(),
    val name: String = "",
    val shortName: String = "",
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val url: String = "https://yandex.ru/pogoda/$shortName"
)