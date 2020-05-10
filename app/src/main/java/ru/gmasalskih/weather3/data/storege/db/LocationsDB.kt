package ru.gmasalskih.weather3.data.storege.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.gmasalskih.weather3.data.entity.Location

@Database(entities = [Location::class], version = 1, exportSchema = false)
abstract class LocationsDB : RoomDatabase() {
    abstract val locationsDao: LocationsDao

    companion object {
        @Volatile
        private var INSTANCE: LocationsDB? = null

        fun getInstance(context: Context): LocationsDB {
            synchronized(this) {
                var instance =
                    INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LocationsDB::class.java,
                        "locations_DB"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }
}
