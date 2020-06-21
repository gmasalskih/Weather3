package ru.gmasalskih.weather3.data.entity.geocoder

import com.google.gson.annotations.SerializedName

data class GeoObjectCollection (
    @SerializedName("metaDataProperty") val metaDataProperty : GeocoderResponseMetaData?,
    @SerializedName("featureMember") val featureMember : List<FeatureMember>?
)