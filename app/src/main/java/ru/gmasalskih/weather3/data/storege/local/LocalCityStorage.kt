package ru.gmasalskih.weather3.data.storege.local

import ru.gmasalskih.weather3.data.City

val localCityStorage = HashSet<City>().apply {
    addAll(
        (1..10).map {
            var city = City(
                name = "City #$it",
                addressLine = "City #$it",
                lat = it.toFloat(),
                lon = it.toFloat()
            )
            if(it%2 == 0){
                LocalFavoriteCityProvider.addCity(city)
            }
            return@map city
        }.toList()
    )
}