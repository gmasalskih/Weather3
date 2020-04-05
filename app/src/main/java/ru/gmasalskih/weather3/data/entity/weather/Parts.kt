package ru.gmasalskih.weather3.data.entity.weather

import com.google.gson.annotations.SerializedName

data class Parts (
	@SerializedName("part_name")
	val part_name : String,

	@SerializedName("temp_min")
	val temp_min : Int,

	@SerializedName("temp_max")
	val temp_max : Int,

	@SerializedName("temp_avg")
	val temp_avg : Int,

	@SerializedName("feels_like")
	val feels_like : Int,

	@SerializedName("icon")
	val icon : String,

	@SerializedName("condition")
	val condition : String,

	@SerializedName("daytime")
	val daytime : String,

	@SerializedName("polar")
	val polar : Boolean,

	@SerializedName("wind_speed")
	val wind_speed : Double,

	@SerializedName("wind_gust")
	val wind_gust : Double,

	@SerializedName("wind_dir")
	val wind_dir : String,

	@SerializedName("pressure_mm")
	val pressure_mm : Int,

	@SerializedName("pressure_pa")
	val pressure_pa : Int,

	@SerializedName("humidity")
	val humidity : Int,

	@SerializedName("prec_mm")
	val prec_mm : Double,

	@SerializedName("prec_period")
	val prec_period : Int,

	@SerializedName("prec_prob")
	val prec_prob : Int
)