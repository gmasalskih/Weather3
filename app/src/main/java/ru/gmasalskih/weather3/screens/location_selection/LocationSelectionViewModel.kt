package ru.gmasalskih.weather3.screens.location_selection

import android.app.Application
import androidx.lifecycle.*
import com.jakewharton.rxbinding3.InitialValueObservable
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.data.storege.internet.GeocoderApi
import ru.gmasalskih.weather3.data.storege.db.LocationsDB
import ru.gmasalskih.weather3.data.storege.local.SharedPreferencesProviderOld
import ru.gmasalskih.weather3.utils.TAG_LOG
import timber.log.Timber

class LocationSelectionViewModel(application: Application) : AndroidViewModel(application) {

    private val db by lazy { LocationsDB.getInstance(getApplication()).locationsDao }
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private val _responseListLocation = MutableLiveData<List<Location>>()
    val responseListLocation: LiveData<List<Location>>
        get() = _responseListLocation

    private val _errorMassage = MutableLiveData<String>()
    val errorMassage: LiveData<String>
        get() = _errorMassage

    init {
        setListLocationsFromDB()
    }

    private fun setListLocationsFromApi(locationName: String) {
        val disposable = GeocoderApi.getListLocations(locationName)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _responseListLocation.value = it
            }, {
                _errorMassage.value = "Что-то пошло не по плану: ${it.message}"
            })
        compositeDisposable.add(disposable)
    }

    private fun setListLocationsFromDB() {
        val disposable = db.getAllLocations()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .filter { !it.isNullOrEmpty() }
            .subscribe({
                _responseListLocation.value = it
            }, {
                _errorMassage.value = "Что-то пошло не по плану: ${it.message}"
            }, {
                _errorMassage.value = "Список пуст!!!"
                _responseListLocation.value = null
            })
        compositeDisposable.add(disposable)
    }

    fun addSelectedLocationToDB(location: Location) {
        val disposable = db.insert(location).subscribeOn(Schedulers.io())
            .subscribe { Timber.i("$TAG_LOG локация добавлена в DB $location") }
        compositeDisposable.add(disposable)
    }

    fun setLastSelectedLocationCoordinates(lat: String, lon: String) {
        SharedPreferencesProviderOld.setLastLocationCoordinates(
            lat = lat,
            lon = lon,
            application = getApplication()
        )
    }

    fun locationFilter(charSequenceObservable: InitialValueObservable<TextViewTextChangeEvent>) {
        val disposable = charSequenceObservable
            .skipInitialValue()
            .distinctUntilChanged()
            .map { it.text.toString() }
            .subscribe {
                if (it.isEmpty()) setListLocationsFromDB()
                else setListLocationsFromApi(it.toString())
            }
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}