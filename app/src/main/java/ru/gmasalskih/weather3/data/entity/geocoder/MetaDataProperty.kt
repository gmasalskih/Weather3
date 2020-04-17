package ru.gmasalskih.weather3.data.entity.geocoder

import com.google.gson.annotations.SerializedName
import ru.gmasalskih.weather3.data.entity.geocoder.GeocoderMetaData

data class MetaDataProperty (
	@SerializedName("GeocoderMetaData")
	val geocoderMetaData : GeocoderMetaData?
)