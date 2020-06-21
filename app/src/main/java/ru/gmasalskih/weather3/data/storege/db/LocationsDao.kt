package ru.gmasalskih.weather3.data.storege.db

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe
import ru.gmasalskih.weather3.data.entity.Location

@Dao
interface LocationsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(location: Location) : Completable

    @Query("SELECT * FROM locations WHERE lat = :lat AND lon = :lon")
    fun getLocation(lat: String, lon: String): Maybe<Location>

    @Query("SELECT * FROM locations WHERE favorite = 1")
    fun getFavoriteLocations(): Maybe<List<Location>>

    @Update
    fun updateLocation(location: Location) :Completable

    @Query("DELETE FROM locations")
    fun clearAllLocations() : Completable

    @Query("UPDATE locations SET favorite = 0 WHERE favorite = 1")
    fun clearAllFavoriteLocations(): Completable

    @Query("SELECT * FROM locations ORDER BY name ASC")
    fun getAllLocations(): Maybe<List<Location>>
}