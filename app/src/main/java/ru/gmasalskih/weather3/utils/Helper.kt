package ru.gmasalskih.weather3.utils

import android.content.Context
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import ru.gmasalskih.weather3.data.entity.Location

const val DATE_PATTERN = "yyyy-MM-dd"
const val TAG_LOG = "---"

fun String.toast(context: Context){
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

@BindingAdapter("cityName")
fun TextView.setCityName(item: Location?) {
    item?.let { location: Location ->
        text = location.name
    }
}

@BindingAdapter("cityLat")
fun TextView.setCityLat(item: Location?) {
    item?.let { location: Location ->
        text = location.lat.toString()
    }
}

@BindingAdapter("cityLon")
fun TextView.setCityLon(item: Location?) {
    item?.let { location: Location ->
        text = location.lon.toString()
    }
}

@BindingAdapter("cityAddressLine")
fun TextView.setCityAddressLine(item: Location?) {
    item?.let { location: Location ->
        text = location.addressLine
    }
}

@BindingAdapter("cityCountryName")
fun TextView.setCityCountryName(item: Location?) {
    item?.let { location: Location ->
        text = location.countryName
    }
}

@BindingAdapter("cityCountyCode")
fun TextView.setCityCountyCode(item: Location?) {
    item?.let { location: Location ->
        text = location.countyCode
    }
}