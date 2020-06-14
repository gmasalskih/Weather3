package ru.gmasalskih.weather3.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.gmasalskih.weather3.data.entity.Coordinates
import ru.gmasalskih.weather3.data.storege.db.LocationsDB
import ru.gmasalskih.weather3.data.storege.db.LocationsDao
import ru.gmasalskih.weather3.data.storege.local.SharedPreferencesProvider
import ru.gmasalskih.weather3.screens.weather.WeatherViewModel
import ru.gmasalskih.weather3.utils.APP_PREFERENCES

val weatherModel = module {

    single<LocationsDao> { LocationsDB.getInstance(androidApplication()).locationsDao }
    single<SharedPreferences> { androidApplication().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE) }
    single<SharedPreferencesProvider> { SharedPreferencesProvider(get()) }

    viewModel { (coordinates: Coordinates) ->
        WeatherViewModel(
            coordinates = coordinates,
            db = get(),
            spp = get()
        )
    }
}