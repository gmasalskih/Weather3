package ru.gmasalskih.weather3.utils

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentActivity
import com.ahmadrosid.svgloader.SvgLoader
import ru.gmasalskih.weather3.R
import ru.gmasalskih.weather3.data.entity.Location

const val TAG_LOG = "LOG"
const val TAG_ERR = "ERR"
const val EMPTY_COORDINATE = "0.0"
const val URL_WEATHER_ICON = "https://yastatic.net/weather/i/icons/blueye/color/svg/"
const val APP_PREFERENCES = "appPreferences"
var USE_GPS = true

fun String.toast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun String.toCoordinate():String{
    if (this.toDoubleOrNull()==null){
        throw IllegalArgumentException("This string can not convert to coordinate")
    } else{
        return "${this.split(".")[0]}.${this.split(".")[1].take(4)}"
    }
}

@BindingAdapter("locationName")
fun TextView.setCityName(item: Location?) {
    item?.let { location: Location ->
        text = location.name
    }
}

@BindingAdapter("locationLat")
fun TextView.setLocationLat(item: Location?) {
    item?.let { location: Location ->
        text = location.lat
    }
}

@BindingAdapter("locationLon")
fun TextView.setLocationLon(item: Location?) {
    item?.let { location: Location ->
        text = location.lon
    }
}

@BindingAdapter("locationAddressLine")
fun TextView.setLocationAddressLine(item: Location?) {
    item?.let { location: Location ->
        text = location.addressLine
    }
}

@BindingAdapter("locationCountryName")
fun TextView.setLocationCountryName(item: Location?) {
    item?.let { location: Location ->
        text = location.countryName
    }
}

@BindingAdapter("locationCountyCode")
fun TextView.setLocationCountyCode(item: Location?) {
    item?.let { location: Location ->
        text = location.countyCode
    }
}

@BindingAdapter("locationID")
fun TextView.setLocationID(item: Location?){
    item?.let {location: Location ->
        text = "lat:${location.lat} lon:${location.lon}"
    }
}

fun ImageView.setWeatherIcon(activity: FragmentActivity, icon: String) {
    SvgLoader.pluck()
        .with(activity)
        .setPlaceHolder(R.drawable.ic_cloud_grey, R.drawable.ic_cloud_off)
        .load("$URL_WEATHER_ICON${icon}.svg", this)
}