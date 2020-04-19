package ru.gmasalskih.weather3.data.providers

import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.data.ILocationProvider
import ru.gmasalskih.weather3.data.storege.local.localLocationsStorage

object LocationProvider : ILocationProvider {

    override fun getLocation(name: String): List<Location> =
        localLocationsStorage.filter { location: Location ->
            location.name.trim().toUpperCase() == name.trim().toUpperCase()
        }

    override fun getLocation(lat: Float, lon: Float): Location? =
        localLocationsStorage.firstOrNull { location: Location ->
            location.lat == lat && location.lon == lon
        }

    override fun addLocation(location: Location): Boolean =
        localLocationsStorage.add(location)

    override fun removeLocation(location: Location): Boolean =
        localLocationsStorage.remove(location)

    override fun removeAllLocations() {
        localLocationsStorage.clear()
    }

    override fun getAllLocations(): List<Location> {
        return localLocationsStorage.toList()
    }
}