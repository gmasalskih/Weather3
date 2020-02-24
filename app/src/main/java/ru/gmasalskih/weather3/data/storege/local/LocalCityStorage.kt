package ru.gmasalskih.weather3.data.storege.local

import ru.gmasalskih.weather3.data.City

val localCityStorage = (1..10).map {
    City(
        name = "City #$it",
        lat = it.toDouble(),
        lon = it.toDouble()
    )
}.toList()