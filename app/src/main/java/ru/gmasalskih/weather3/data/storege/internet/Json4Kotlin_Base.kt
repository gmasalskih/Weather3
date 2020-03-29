package ru.gmasalskih.weather3.data.storege.internet

import com.google.gson.annotations.SerializedName

data class Json4Kotlin_Base (
	@SerializedName("response")
	val response : Response
)