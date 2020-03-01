package ru.gmasalskih.weather3.data

enum class Cloudiness(val value: Double) {
    CLEAR(0.0),
    PARTLY_CLOUDY(0.25),
    CLOUDY_CLEAR_AT_TIMES(0.5),
    CLOUDY_WITH_CLEARINGS(0.75),
    OVERCAST(1.0)
}