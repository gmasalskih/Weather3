package ru.gmasalskih.weather3.data.storege.local

import ru.gmasalskih.weather3.data.City

val localCityStorage = HashSet<City>().apply {
    addAll(
        (1..10).map {
            var city = City(
                name = "City #$it",
                shortName = "City #$it",
                lat = it.toDouble(),
                lon = it.toDouble()
            )
            if(it%2 == 0){
                LocalFavoriteCityProvider.addCity(city)
            }
            return@map city
        }.toList()
    )
}