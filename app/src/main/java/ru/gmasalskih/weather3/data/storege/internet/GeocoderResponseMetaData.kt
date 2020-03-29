package ru.gmasalskih.weather3.data.storege.internet

import com.google.gson.annotations.SerializedName

data class GeocoderResponseMetaData (
	@SerializedName("boundedBy") val boundedBy : BoundedBy,
	@SerializedName("request") val request : String,
	@SerializedName("results") val results : Int,
	@SerializedName("found") val found : Int
)