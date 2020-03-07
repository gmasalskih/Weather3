package ru.gmasalskih.weather3.data.storege.local

import ru.gmasalskih.weather3.data.*
import ru.gmasalskih.weather3.utils.DATE_PATTERN
import java.text.SimpleDateFormat
import java.util.*

val localWeatherStorage = (1..10).map { cityNum: Int ->
        (0..6).map { dayNum: Int ->
            Weather(
                city = LocalCityProvider.getCity("City #$cityNum"),
                temp = cityNum,
                timestamp = addDay(dayNum),
                windSpeed = cityNum,
                pressure = cityNum,
                humidity = cityNum,
                condition = WeatherCondition.values().random(),
                windDirection = WindDirection.values().random(),
                precipitationType = PrecipitationType.values().random(),
                precipitationStrength = PrecipitationStrength.values().random(),
                cloudiness = Cloudiness.values().random()
            )
        }
    }
    .flatten()
    .toList()

fun addDay(day: Int): String = SimpleDateFormat(DATE_PATTERN).format(
    Calendar.getInstance().apply {
        add(Calendar.DAY_OF_MONTH, day)
    }.time
)