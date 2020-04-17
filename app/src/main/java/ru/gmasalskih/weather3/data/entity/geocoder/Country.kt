package ru.gmasalskih.weather3.data.entity.geocoder

import com.google.gson.annotations.SerializedName
import ru.gmasalskih.weather3.data.entity.geocoder.AdministrativeArea

data class Country (
	@SerializedName("AddressLine")
	val addressLine : String?,

	@SerializedName("CountryNameCode")
	val countryNameCode : String?,

	@SerializedName("CountryName")
	val countryName : String?,

	@SerializedName("AdministrativeArea")
	val administrativeArea : AdministrativeArea?
)