package ru.gmasalskih.weather3.data.entity.geocoder

import com.google.gson.annotations.SerializedName
import ru.gmasalskih.weather3.data.entity.geocoder.FeatureMember
import ru.gmasalskih.weather3.data.entity.geocoder.GeocoderResponseMetaData

data class GeoObjectCollection (
    @SerializedName("metaDataProperty") val metaDataProperty : GeocoderResponseMetaData?,
    @SerializedName("featureMember") val featureMember : List<FeatureMember>?
)