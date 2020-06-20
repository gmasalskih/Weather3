package ru.gmasalskih.weather3.di

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.gmasalskih.weather3.data.entity.Coordinates
import ru.gmasalskih.weather3.data.storege.db.LocationsDB
import ru.gmasalskih.weather3.data.storege.db.LocationsDao
import ru.gmasalskih.weather3.data.storege.gps.CoordinatesProvider
import ru.gmasalskih.weather3.data.storege.local.SharedPreferencesProvider
import ru.gmasalskih.weather3.screens.location_selection.LocationSelectionViewModel
import ru.gmasalskih.weather3.screens.weather.WeatherViewModel
import ru.gmasalskih.weather3.utils.APP_PREFERENCES

val providersModule = module {
    single<LocationsDao> { LocationsDB.getInstance(androidApplication()).locationsDao }
    single<CoordinatesProvider> { CoordinatesProvider(androidContext()) }
    single<SharedPreferences> {
        androidApplication().getSharedPreferences(
            APP_PREFERENCES,
            Context.MODE_PRIVATE
        )
    }
    single<SharedPreferencesProvider> { SharedPreferencesProvider(get()) }
}

val weatherModule = module {
    viewModel { (coordinates: Coordinates) ->
        WeatherViewModel(
            coordinates = coordinates,
            db = get(),
            spp = get(),
            cp = get()
        )
    }
}

val locationSelectionModule = module {
    viewModel {
        LocationSelectionViewModel(
            db = get(),
            spp = get()
        )
    }
}