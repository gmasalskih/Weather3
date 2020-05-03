package ru.gmasalskih.weather3.data.providers

import ru.gmasalskih.weather3.data.ILocationProvider
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.data.storege.db.LocationsDao

//class LocationProviderDB(private val dao: LocationsDao) : ILocationProvider {

//    override fun getLocation(name: String): List<Location> {
//        return dao.getLocation(name)
//    }
//
//    override fun getLocation(lat: Float, lon: Float): Location? {
//        return dao.getLocation(lat = lat, lon = lon)
//    }
//
//    override fun addLocation(location: Location): Boolean {
//        dao.insert(location)
//        return true
//    }
//
//    override fun removeLocation(location: Location): Boolean {
//        dao.delete(location)
//        return true
//    }
//
//    override fun removeAllLocations() {
//        dao.clear()
//    }
//
//    override fun getAllLocations(): List<Location> {
//        return dao.getAllLocations().value!!
//    }
//}