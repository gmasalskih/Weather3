package ru.gmasalskih.weather3

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.gmasalskih.weather3.di.*
import timber.log.Timber

class Weather3Application : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidLogger()
            androidContext(this@Weather3Application)
            modules(
                providersModule,
                weatherModule,
                locationSelectionModule,
                settingsModule,
                favoriteLocationModule
            )
        }
    }
}
