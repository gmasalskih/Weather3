package ru.gmasalskih.weather3.screens.favorite_location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.gmasalskih.weather3.data.entity.Coordinates
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.data.storege.db.LocationsDao
import ru.gmasalskih.weather3.data.storege.local.SharedPreferencesProvider
import ru.gmasalskih.weather3.utils.TAG_ERR
import timber.log.Timber

class FavoriteLocationViewModel(
    private val db: LocationsDao,
    private val spp: SharedPreferencesProvider
) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var _favoriteLocationList = MutableLiveData<List<Location>>()
    val favoriteLocationList: LiveData<List<Location>>
        get() = _favoriteLocationList

    private val _massage = MutableLiveData<String>()
    val massage : LiveData<String>
        get() = _massage

    private val _errorMassage = MutableLiveData<String>()
    val errorMassage: LiveData<String>
        get() = _errorMassage

    init {
        setFavoriteLocations()
    }

    fun onDeleteFavoriteCity(location: Location) {
        val disposable = db.updateLocation(location.apply { isFavorite = false })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _massage.value = "Location $location was deleted"
                setFavoriteLocations()
            }
        compositeDisposable.add(disposable)
    }

    fun setLastSelectedLocationCoordinates(coordinates: Coordinates) {
        spp.setLastLocationCoordinates(coordinates)
    }

    private fun setFavoriteLocations() {
        val disposable = db.getFavoriteLocations()
            .subscribeOn(Schedulers.io())
            .filter { it.isNotEmpty() }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _favoriteLocationList.value = it
            }, {
                _errorMassage.value = "Что-то пошло не по плану: ${it.message}"
                Timber.d(it, "$TAG_ERR Что-то пошло не по плану")
            }, {
                _favoriteLocationList.value = null
                _errorMassage.value = "Список любимых городов пуст"
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}