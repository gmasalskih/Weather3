package ru.gmasalskih.weather3.data.entity.geocoder

import com.google.gson.annotations.SerializedName

data class Envelope (
	@SerializedName("lowerCorner")
	val lowerCorner : String?,

	@SerializedName("upperCorner")
	val upperCorner : String?
)