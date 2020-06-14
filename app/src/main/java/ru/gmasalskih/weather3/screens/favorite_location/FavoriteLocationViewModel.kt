package ru.gmasalskih.weather3.screens.favorite_location

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.data.storege.db.LocationsDB
import ru.gmasalskih.weather3.data.storege.local.SharedPreferencesProviderOld
import ru.gmasalskih.weather3.utils.TAG_ERR
import timber.log.Timber

class FavoriteLocationViewModel(application: Application) : AndroidViewModel(application) {

    private val db = LocationsDB.getInstance(getApplication()).locationsDao

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var _favoriteLocationList = MutableLiveData<List<Location>>()
    val favoriteLocationList: LiveData<List<Location>>
        get() = _favoriteLocationList

    private val _errorMassage = MutableLiveData<String>()
    val errorMassage: LiveData<String>
        get() = _errorMassage

    init {
        setFavoriteLocations()
    }

    fun onDeleteFavoriteCity(location: Location) {
        val disposable = db.updateLocation(location.apply { isFavorite = false })
            .subscribeOn(Schedulers.io())
            .subscribe { setFavoriteLocations() }


    }

    fun setLastSelectedLocationCoordinates(lat: String, lon: String) {
        SharedPreferencesProviderOld.setLastLocationCoordinates(
            lat = lat,
            lon = lon,
            application = getApplication()
        )
    }

    private fun setFavoriteLocations() {
        val disposable = db.getFavoriteLocations().subscribeOn(Schedulers.io())
            .filter { !it.isNullOrEmpty() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _favoriteLocationList.value = it
            }, {
                _errorMassage.value = "Что-то пошло не по плану: ${it.message}"
                Timber.d(it, "$TAG_ERR Что-то пошло не по плану")
            }, {
                _errorMassage.value = "Список любимых городов пуст"
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}