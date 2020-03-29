package ru.gmasalskih.weather3.data.storege.internet

import com.google.gson.annotations.SerializedName

data class BoundedBy (
	@SerializedName("Envelope")
	val envelope : Envelope
)