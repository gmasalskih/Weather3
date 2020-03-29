package ru.gmasalskih.weather3.data.storege.internet

import com.google.gson.annotations.SerializedName

data class Point (
	@SerializedName("pos")
	val pos : String
)