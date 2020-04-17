package ru.gmasalskih.weather3.data.entity.geocoder

import com.google.gson.annotations.SerializedName
import ru.gmasalskih.weather3.data.entity.geocoder.Address
import ru.gmasalskih.weather3.data.entity.geocoder.AddressDetails

data class GeocoderMetaData (
    @SerializedName("precision")
	val precision : String?,

    @SerializedName("text")
	val text : String?,

    @SerializedName("kind")
	val kind : String?,

    @SerializedName("Address")
	val address : Address?,

    @SerializedName("AddressDetails")
	val addressDetails : AddressDetails?
)