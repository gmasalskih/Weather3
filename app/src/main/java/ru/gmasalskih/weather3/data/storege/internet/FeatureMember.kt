package ru.gmasalskih.weather3.data.storege.internet

import com.google.gson.annotations.SerializedName

data class FeatureMember (
	@SerializedName("GeoObject")
	val geoObject : GeoObject
)