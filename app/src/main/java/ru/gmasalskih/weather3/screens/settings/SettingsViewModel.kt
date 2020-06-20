package ru.gmasalskih.weather3.screens.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.gmasalskih.weather3.data.storege.db.LocationsDao

class SettingsViewModel(private val db: LocationsDao) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    fun onClearHistory() {
        val disposable = db.clearAllLocations()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _message.value = "Clear history done" }
        compositeDisposable.add(disposable)
    }

    fun onRemoveFavoritesLocations() {
        val disposable = db.clearAllFavoriteLocations()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _message.value = "Remove favorites locations done" }
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}