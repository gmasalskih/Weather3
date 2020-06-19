package ru.gmasalskih.weather3.screens.weather

import androidx.lifecycle.*
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.gmasalskih.weather3.data.entity.Coordinates
import ru.gmasalskih.weather3.data.storege.internet.GeocoderApi
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.data.entity.Weather
import ru.gmasalskih.weather3.data.storege.db.LocationsDao
import ru.gmasalskih.weather3.data.storege.gps.CoordinatesProvider
import ru.gmasalskih.weather3.data.storege.internet.WeatherApi
import ru.gmasalskih.weather3.data.storege.local.SharedPreferencesProvider
import ru.gmasalskih.weather3.utils.TAG_ERR
import ru.gmasalskih.weather3.utils.USE_GPS
import timber.log.Timber

class WeatherViewModel(
    private var coordinates: Coordinates,
    private val db: LocationsDao,
    private val spp: SharedPreferencesProvider,
    private val cp: CoordinatesProvider
) : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val _currentLocation = MutableLiveData<Location>()
    val currentLocation: LiveData<Location>
        get() = _currentLocation

    private val _errorMassage = MutableLiveData<String>()
    val errorMassage: LiveData<String>
        get() = _errorMassage

    private val _isGPSAlloy = MutableLiveData<Boolean>()
    val isGPSAlloy: LiveData<Boolean>
        get() = _isGPSAlloy

    private val _isCurrentCoordinateEmpty = MutableLiveData<Boolean>()
    val isCurrentCoordinateEmpty: LiveData<Boolean>
        get() = _isCurrentCoordinateEmpty

    private val _currentWeather = MutableLiveData<Weather>()
    val currentWeather: LiveData<Weather>
        get() = _currentWeather

    private val _isLocationSelected = MutableLiveData<Boolean>()
    val isLocationSelected: LiveData<Boolean>
        get() = _isLocationSelected

    private val _isLocationWebPageSelected = MutableLiveData<Boolean>()
    val isLocationWebPageSelected: LiveData<Boolean>
        get() = _isLocationWebPageSelected

    private val _isLocationFavorite = MutableLiveData<Boolean>()
    val isLocationFavorite: LiveData<Boolean>
        get() = _isLocationFavorite

    private val _isCurrentLocationSelected = MutableLiveData<Boolean>()
    val isCurrentLocationSelected: LiveData<Boolean>
        get() = _isCurrentLocationSelected

    init {
        _isCurrentCoordinateEmpty.value = false
        _isLocationSelected.value = false
        _isLocationFavorite.value = false
        _isLocationWebPageSelected.value = false
        _isGPSAlloy.value = USE_GPS
    }

    fun aaa() {
        cp.retrieveLastKnownCoordinate(onSuccess = {
            coordinates = it
            initCoordinates()
        }, onDeny = {

        }, onEmpty = {

        })
    }

    fun initCoordinates() {
        val disposable = Observable.just(coordinates, spp.getLastLocationCoordinates())
            .filter { !it.isCoordinatesEmpty() }
            .firstElement()
            .subscribe({ coordinates ->
                _isCurrentCoordinateEmpty.value = false
                initLocation(coordinates)
            }, {
                _isCurrentCoordinateEmpty.value = true
                Timber.d("$TAG_ERR ${it.message}")
            }, {
                _isCurrentCoordinateEmpty.value = true
            })
        compositeDisposable.add(disposable)
    }

    private fun initLocation(coordinates: Coordinates) {
        val fromDB = db.getLocation(lat = coordinates.lat, lon = coordinates.lon)
            .map { Pair("fromDB", it) }
        val fromApi = GeocoderApi.getLocation(coordinates)
            .map { Pair("fromApi", it) }

        val disposable = Maybe.concat(fromDB, fromApi)
            .subscribeOn(Schedulers.io())
            .filter { it.second.name.isNotEmpty() }
            .doOnNext { if (it.first == "fromApi") db.insert(it.second) }
            .map { it.second }
            .firstElement()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                initWeather(it)
                _currentLocation.value = it
                _isLocationFavorite.value = it.isFavorite
            }, {
                _errorMassage.value = "Местоположение не найдено"
                Timber.d("$TAG_ERR ${it.message}")
            }, {
                _errorMassage.value = "Местоположение не найдено"
            })

        compositeDisposable.add(disposable)
    }

    private fun initWeather(location: Location) {
        val disposable = WeatherApi.getResponse(location)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _currentWeather.value = it
            }, {
                _errorMassage.value = "Погода не найдена"
                Timber.d("$TAG_ERR ${it.message}")
            }, {
                _errorMassage.value = "Погода не найдена"
            })
        compositeDisposable.add(disposable)
    }

    // Click Event
    fun onLocationSelect() {
        _isLocationSelected.value = true
        _isLocationSelected.value = false
    }

    fun onLocationWebPageSelect() {
        _isLocationWebPageSelected.value = true
        _isLocationWebPageSelected.value = false
    }

    fun onCurrentLocationSelected() {
        _isCurrentLocationSelected.value = true
        _isCurrentLocationSelected.value = false
    }

    fun onToggleFavoriteLocation() {

//        val location = db.getLocation(lat = lat, lon = lon).first()
//        _isLocationFavoriteSelected.value?.let { event: Boolean ->
//            location.isFavorite = !event
//            db.updateLocation(location)
//            updateCurrentLocation()
//        }

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}
