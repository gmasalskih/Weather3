package ru.gmasalskih.weather3.data.entity.weather

import com.google.gson.annotations.SerializedName

data class Forecast (
	@SerializedName("date")
	val date : String,

	@SerializedName("date_ts")
	val date_ts : Int,

	@SerializedName("week")
	val week : Int,

	@SerializedName("sunrise")
	val sunrise : String,

	@SerializedName("sunset")
	val sunset : String,

	@SerializedName("moon_code")
	val moon_code : Int,

	@SerializedName("moon_text")
	val moon_text : String,

	@SerializedName("parts")
	val parts : List<Parts>
)