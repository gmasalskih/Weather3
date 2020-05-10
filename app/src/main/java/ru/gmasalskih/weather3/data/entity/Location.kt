package ru.gmasalskih.weather3.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations", primaryKeys = ["lat", "lon"])
data class Location(
    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "address_line")
    val addressLine: String = "",

    @ColumnInfo(name = "country_name")
    val countryName: String = "",

    @ColumnInfo(name = "county_code")
    val countyCode: String = "",

    @ColumnInfo(name = "lat")
    val lat: String = "0.0",

    @ColumnInfo(name = "lon")
    val lon: String = "0.0",

    @ColumnInfo(name = "favorite")
    var isFavorite: Boolean = false
)