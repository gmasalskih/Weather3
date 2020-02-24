package ru.gmasalskih.weather3

import android.app.Application
import timber.log.Timber

class Weather3Application: Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}