package ru.gmasalskih.weather3.screens.favorite_city

import android.widget.TextView
import androidx.databinding.BindingAdapter
import ru.gmasalskih.weather3.data.City

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
