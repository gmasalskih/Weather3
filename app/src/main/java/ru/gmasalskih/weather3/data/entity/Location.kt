package ru.gmasalskih.weather3.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
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
    val lat: Float = 0.0F,

    @ColumnInfo(name = "lon")
    val lon: Float = 0.0F
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0L
}