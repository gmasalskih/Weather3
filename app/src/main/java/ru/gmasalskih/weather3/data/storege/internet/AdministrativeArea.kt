package ru.gmasalskih.weather3.data.storege.internet

import com.google.gson.annotations.SerializedName

data class AdministrativeArea (
	@SerializedName("AdministrativeAreaName")
	val administrativeAreaName : String
)