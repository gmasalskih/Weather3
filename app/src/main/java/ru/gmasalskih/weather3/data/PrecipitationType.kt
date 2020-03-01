package ru.gmasalskih.weather3.data

enum class PrecipitationType(val value:Int) {
    NO_PRECIPITATION(0),
    RAIN(1),
    RAIN_WITH_SNOW(2),
    SNOW(3)
}