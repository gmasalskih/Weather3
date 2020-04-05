package ru.gmasalskih.weather3.data.entity.geocoder

import com.google.gson.annotations.SerializedName

data class AddressDetails (
	@SerializedName("Country")
	val country : Country
)