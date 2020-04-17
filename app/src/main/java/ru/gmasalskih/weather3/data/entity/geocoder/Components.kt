package ru.gmasalskih.weather3.data.entity.geocoder

import com.google.gson.annotations.SerializedName

data class Components (
	@SerializedName("kind") val kind : String?,
	@SerializedName("name") val name : String?
)