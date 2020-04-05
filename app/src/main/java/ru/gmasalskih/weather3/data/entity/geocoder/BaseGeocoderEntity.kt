package ru.gmasalskih.weather3.data.entity.geocoder

import com.google.gson.annotations.SerializedName

data class BaseGeocoderEntity (
	@SerializedName("response")
	val response : Response
)