package ru.gmasalskih.weather3.data.entity.geocoder

import com.google.gson.annotations.SerializedName

data class Point (
	@SerializedName("pos")
	val pos : String
)