package ru.gmasalskih.weather3

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.data.storege.db.LocationsDB
import ru.gmasalskih.weather3.data.storege.db.LocationsDao
import java.io.IOException
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class LocationsDBTest {

    private lateinit var locationsDao: LocationsDao
    private lateinit var db: LocationsDB
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Before
    fun createDB(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, LocationsDB::class.java)
            .build()
        locationsDao = db.locationsDao
    }

    @After
    @Throws(IOException::class)
    fun closeDB(){
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun test(){
        val location = Location(name = "City")
        coroutineScope.launch {
            locationsDao.insert(location)
            val lastInsert = locationsDao.getLocation("0.0", "0.0")
            assertEquals(lastInsert.first().name, "City")
        }
    }
}

