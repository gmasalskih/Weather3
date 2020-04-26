package ru.gmasalskih.weather3.utils

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import ru.gmasalskih.weather3.data.entity.Location

const val DATE_PATTERN = "yyyy-MM-dd"
const val TAG_LOG = "---"
const val URL_WEATHER_ICON = "https://yastatic.net/weather/i/icons/blueye/color/svg/"

fun String.toast(context: Context){
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
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
        text = location.lat.toString()
    }
}

@BindingAdapter("locationLon")
fun TextView.setLocationLon(item: Location?) {
    item?.let { location: Location ->
        text = location.lon.toString()
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