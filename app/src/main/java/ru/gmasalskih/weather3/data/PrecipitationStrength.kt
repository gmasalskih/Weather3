package ru.gmasalskih.weather3.data

enum class PrecipitationStrength(value:Double) {
    NO_PRECIPITATION(0.0),
    LIGHT_RAIN_OR_SNOW(0.25),
    RAIN_OR_SNOW(0.5),
    HEAVY_RAIN_OR_SNOW(0.75),
    HEAVY_RAINFALL_OR_SNOWFALL(1.0)
}