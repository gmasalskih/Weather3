package ru.gmasalskih.weather3.screens.city_selection

import android.widget.TextView
import androidx.databinding.BindingAdapter
import ru.gmasalskih.weather3.data.CitySelection

@BindingAdapter("cityAddressLine")
fun TextView.setCityAddressLine(item: CitySelection?) {
    item?.let { city: CitySelection ->
        text = city.addressLine
    }
}

@BindingAdapter("cityCountryName")
fun TextView.setCityCountryName(item: CitySelection?) {
    item?.let { city: CitySelection ->
        text = city.countryName
    }
}

@BindingAdapter("cityCountyCode")
fun TextView.setCityCountyCode(item: CitySelection?) {
    item?.let { city: CitySelection ->
        text = city.countyCode
    }
}
