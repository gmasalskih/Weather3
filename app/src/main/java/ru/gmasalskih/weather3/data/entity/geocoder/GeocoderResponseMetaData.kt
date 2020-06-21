package ru.gmasalskih.weather3.data.entity.geocoder

import com.google.gson.annotations.SerializedName

data class GeocoderResponseMetaData (
    @SerializedName("boundedBy") val boundedBy : BoundedBy?,
    @SerializedName("request") val request : String?,
    @SerializedName("results") val results : Int?,
    @SerializedName("found") val found : Int?
)