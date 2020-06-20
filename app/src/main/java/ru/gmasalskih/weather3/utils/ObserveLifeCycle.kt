package ru.gmasalskih.weather3.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import timber.log.Timber

class ObserveLifeCycle(lifecycle: Lifecycle) : LifecycleObserver {
    init {
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun statusLifecycle(source: LifecycleOwner, event: Lifecycle.Event){
        Timber.i("$TAG_LOG ${source.javaClass.simpleName} - ${event.name}")
    }

}