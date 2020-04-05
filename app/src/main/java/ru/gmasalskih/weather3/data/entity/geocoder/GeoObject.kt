package ru.gmasalskih.weather3.data.entity.geocoder

import com.google.gson.annotations.SerializedName

data class GeoObject (
    @SerializedName("metaDataProperty") val metaDataProperty : MetaDataProperty,
    @SerializedName("name") val name : String,
    @SerializedName("description") val description : String,
    @SerializedName("boundedBy") val boundedBy : BoundedBy,
    @SerializedName("Point") val point : Point
)