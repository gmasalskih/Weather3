package ru.gmasalskih.weather3.data.storege.internet

import com.google.gson.annotations.SerializedName

data class MetaDataProperty (
	@SerializedName("GeocoderMetaData")
	val geocoderMetaData : GeocoderMetaData
)