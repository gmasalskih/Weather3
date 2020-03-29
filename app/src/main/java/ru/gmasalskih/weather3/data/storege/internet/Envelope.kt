package ru.gmasalskih.weather3.data.storege.internet

import com.google.gson.annotations.SerializedName

data class Envelope (
	@SerializedName("lowerCorner")
	val lowerCorner : String,

	@SerializedName("upperCorner")
	val upperCorner : String
)