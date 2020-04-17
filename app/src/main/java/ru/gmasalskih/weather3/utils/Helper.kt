package ru.gmasalskih.weather3.utils

import android.content.Context
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import ru.gmasalskih.weather3.data.entity.City

const val DATE_PATTERN = "yyyy-MM-dd"
const val TAG_LOG = "---"

fun String.toast(context: Context){
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

@BindingAdapter("cityName")
fun TextView.setCityName(item: City?) {
    item?.let { city: City ->
        text = city.name
    }
}

@BindingAdapter("cityLat")
fun TextView.setCityLat(item: City?) {
    item?.let { city: City ->
        text = city.lat.toString()
    }
}

@BindingAdapter("cityLon")
fun TextView.setCityLon(item: City?) {
    item?.let { city: City ->
        text = city.lon.toString()
    }
}

@BindingAdapter("cityAddressLine")
fun TextView.setCityAddressLine(item: City?) {
    item?.let { city: City ->
        text = city.addressLine
    }
}

@BindingAdapter("cityCountryName")
fun TextView.setCityCountryName(item: City?) {
    item?.let { city: City ->
        text = city.countryName
    }
}

@BindingAdapter("cityCountyCode")
fun TextView.setCityCountyCode(item: City?) {
    item?.let { city: City ->
        text = city.countyCode
    }
}