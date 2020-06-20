package ru.gmasalskih.weather3.data.entity

import ru.gmasalskih.weather3.utils.EMPTY_COORDINATE

data class Coordinates(val lat: String, val lon:String){
    fun isCoordinatesEmpty() = this.lat == EMPTY_COORDINATE || this.lon == EMPTY_COORDINATE
    fun isCoordinatesNotEmpty() = this.lat != EMPTY_COORDINATE && this.lon != EMPTY_COORDINATE
}