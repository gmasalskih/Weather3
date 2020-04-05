package ru.gmasalskih.weather3.data.entity.geocoder

import com.google.gson.annotations.SerializedName

data class Address (
	@SerializedName("country_code")
	val country_code : String,

	@SerializedName("formatted")
	val formatted : String,

	@SerializedName("Components")
	val components : List<Components>
)