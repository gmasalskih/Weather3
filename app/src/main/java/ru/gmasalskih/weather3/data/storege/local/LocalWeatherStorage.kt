package ru.gmasalskih.weather3.data.storege.local

import ru.gmasalskih.weather3.data.*

val localWeatherStorage = (1..7).map {
    Weather(
        city = localCityStorage[it-1],
        temp = it,
        timestamp = "2020-01-0$it",
        windSpeed = it,
        pressure = it,
        humidity = it,
        condition = WeatherCondition.values().random(),
        windDirection = WindDirection.values().random(),
        precipitationType = PrecipitationType.values().random(),
        precipitationStrength = PrecipitationStrength.values().random(),
        cloudiness = Cloudiness.values().random()
    )
}.toList()