package ru.gmasalskih.weather3.data

enum class WeatherCondition(value:String) {
    CLEAR("clear"),
    PARTLY_CLOUDY("partly-cloudy"),
    CLOUDY("cloudy"),
    OVERCAST("overcast"),
    PARTLY_CLOUDY_AND_LIGHT_RAIN("partly-cloudy-and-light-rain"),
    PARTLY_CLOUDY_AND_RAIN("partly-cloudy-and-rain"),
    OVERCAST_AND_RAIN("overcast-and-rain"),
    OVERCAST_THUNDERSTORMS_WITH_RAIN("overcast-thunderstorms-with-rain"),
    CLOUDY_AND_LIGHT_RAIN("cloudy-and-light-rain"),
    OVERCAST_AND_LIGHT_RAIN("overcast-and-light-rain"),
    CLOUDY_AND_RAIN("cloudy-and-rain"),
    OVERCAST_AND_WET_SNOW("overcast-and-wet-snow"),
    PARTLY_CLOUDY_AND_LIGHT_SNOW("partly-cloudy-and-light-snow"),
    PARTLY_CLOUDY_AND_SNOW("partly-cloudy-and-snow"),
    OVERCAST_AND_SNOW("overcast-and-snow"),
    CLOUDY_AND_LIGHT_SNOW("cloudy-and-light-snow"),
    OVERCAST_AND_LIGHT_SNOW("overcast-and-light-snow"),
    CLOUDY_AND_SNOW("cloudy-and-snow")
}