package ru.gmasalskih.weather3.data.entity.geocoder

import com.google.gson.annotations.SerializedName

data class AdministrativeArea (
	@SerializedName("AdministrativeAreaName")
	val administrativeAreaName : String
)