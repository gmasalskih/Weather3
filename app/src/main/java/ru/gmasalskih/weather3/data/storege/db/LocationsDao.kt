package ru.gmasalskih.weather3.data.storege.db

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.gmasalskih.weather3.data.entity.Location

@Dao
interface LocationsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(location: Location)

    @Query("SELECT * FROM locations WHERE lat = :lat AND lon = :lon")
    fun getLocation(lat: Float, lon: Float): Location?

    @Query("SELECT * FROM locations WHERE name = :name")
    fun getLocation(name: String): List<Location>

    @Delete
    fun delete(location: Location)

    @Query("DELETE FROM locations")
    fun clear()

    @Query("SELECT * FROM locations ORDER BY name ASC")
    fun getAllLocations(): LiveData<List<Location>>
}