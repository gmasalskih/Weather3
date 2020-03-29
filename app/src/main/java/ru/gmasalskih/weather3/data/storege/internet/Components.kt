package ru.gmasalskih.weather3.data.storege.internet

import com.google.gson.annotations.SerializedName

data class Components (
	@SerializedName("kind") val kind : String,
	@SerializedName("name") val name : String
)