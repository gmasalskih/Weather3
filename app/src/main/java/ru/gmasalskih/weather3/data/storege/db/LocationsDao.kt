package ru.gmasalskih.weather3.data.storege.db

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.gmasalskih.weather3.data.entity.Location

@Dao
interface LocationsDao {

    @Query("SELECT * FROM locations WHERE lat = :lat AND lon = :lon")
    suspend fun getLocation(lat: String, lon: String): Location?

    @Query("SELECT * FROM locations WHERE name = :name")
    fun getLocation(name: String): LiveData<List<Location>>

    @Query("SELECT * FROM locations ORDER BY name ASC")
    fun getAllLocations(): LiveData<List<Location>>

    @Query("SELECT * FROM locations WHERE favorite = 1")
    fun getFavoriteLocations(): LiveData<List<Location>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: Location)

    @Update
    suspend fun updateLocation(location: Location)

    @Delete
    suspend fun delete(location: Location)

    @Query("DELETE FROM locations")
    suspend fun clearAllLocations()

    @Query("UPDATE locations SET favorite = 0 WHERE favorite = 1")
    suspend fun clearAllFavoriteLocations()
}