package ru.gmasalskih.weather3.data.storege.internet

import com.google.gson.annotations.SerializedName

data class AddressDetails (
	@SerializedName("Country")
	val country : Country
)