package ru.gmasalskih.weather3.data.storege.db

import androidx.room.*
import ru.gmasalskih.weather3.data.entity.Location

@Dao
interface LocationsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(location: Location)

    @Query("SELECT * FROM locations WHERE lat = :lat AND lon = :lon")
    suspend fun getLocation(lat: String, lon: String): List<Location>

    @Query("SELECT * FROM locations WHERE favorite = 1")
    suspend fun getFavoriteLocations(): List<Location>

    @Query("SELECT * FROM locations WHERE name = :name")
    suspend fun getLocation(name: String): List<Location>

    @Update
    suspend fun updateLocation(location: Location)

    @Delete
    suspend fun delete(location: Location)

    @Query("DELETE FROM locations")
    suspend fun clear()

    @Query("SELECT * FROM locations ORDER BY name ASC")
    suspend fun getAllLocations(): List<Location>
}