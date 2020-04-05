package ru.gmasalskih.weather3.data.entity.weather

import com.google.gson.annotations.SerializedName

data class BaseWeatherEntity (
	@SerializedName("now")
	val now : Int,

	@SerializedName("now_dt")
	val now_dt : String,

	@SerializedName("info")
	val info : Info,

	@SerializedName("fact")
	val fact : Fact,

	@SerializedName("forecast")
	val forecast : Forecast
)