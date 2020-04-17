package ru.gmasalskih.weather3.data.entity.geocoder

import com.google.gson.annotations.SerializedName
import ru.gmasalskih.weather3.data.entity.geocoder.GeoObjectCollection

data class Response (
	@SerializedName("GeoObjectCollection")
	val geoObjectCollection : GeoObjectCollection?
)