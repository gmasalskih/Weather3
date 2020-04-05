package ru.gmasalskih.weather3.screens.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.gmasalskih.weather3.data.*
import ru.gmasalskih.weather3.data.entity.geocoder.BaseGeocoderEntity
import ru.gmasalskih.weather3.data.entity.weather.BaseWeatherEntity
import ru.gmasalskih.weather3.data.storege.ICityProvider
import ru.gmasalskih.weather3.data.storege.IFavoriteCityProvider
import ru.gmasalskih.weather3.data.storege.IWeatherProvider
import ru.gmasalskih.weather3.data.storege.local.LocalCityProvider
import ru.gmasalskih.weather3.data.storege.local.LocalFavoriteCityProvider
import ru.gmasalskih.weather3.data.storege.local.LocalWeatherProvider
import timber.log.Timber

class WeatherViewModel(
    var cityName: String,
    var lon: Float,
    var lat: Float
) : ViewModel() {

    private val weatherProvider: IWeatherProvider = LocalWeatherProvider
    private val favoriteCityProvider: IFavoriteCityProvider = LocalFavoriteCityProvider
    private val cityProvider: ICityProvider = LocalCityProvider
    private var city: City = City(name = cityName, lon = lon, lat = lat)

    private val _currentWeather = MutableLiveData<Weather>()
    val currentWeather: LiveData<Weather>
        get() = _currentWeather

    private val _isCitySelected = MutableLiveData<Boolean>()
    val isCitySelected: LiveData<Boolean>
        get() = _isCitySelected

    private val _isCityWebPageSelected = MutableLiveData<Boolean>()
    val isCityWebPageSelected: LiveData<Boolean>
        get() = _isCityWebPageSelected

    private val _isDateSelected = MutableLiveData<Boolean>()
    val isDateSelected: LiveData<Boolean>
        get() = _isDateSelected

    private val _isCityFavoriteSelected = MutableLiveData<Boolean>()
    val isCityFavoriteSelected: LiveData<Boolean>
        get() = _isCityFavoriteSelected


    init {
        cityProvider.addCity(city)
        Timber.i("--- WeatherViewModel created!")
        getResponse(lon, lat)
        updateFavoriteCityStatus()
        _isCitySelected.value = false
        _isDateSelected.value = false
        _isCityWebPageSelected.value = false
    }

    fun getResponse(lon: Float, lat: Float) {
        Timber.i("--- $lon $lat")
        WeatherApi.apiService.getWeather(lon = lon, lat = lat).enqueue(object :
            Callback<BaseWeatherEntity> {
            override fun onFailure(call: Call<BaseWeatherEntity>, t: Throwable) {
                Timber.i("--- ${t.message}")
            }

            override fun onResponse(
                call: Call<BaseWeatherEntity>,
                response: Response<BaseWeatherEntity>
            ) {
                val result: BaseWeatherEntity? = response.body()
                result?.let {
                    _currentWeather.value = Weather(
                        city = city,
                        temp = it.fact.temp,
                        timestamp = it.now_dt,
                        windSpeed = it.fact.wind_speed.toInt(),
                        pressure = it.fact.pressure_mm,
                        humidity = it.fact.humidity
                    )
                }
            }
        })
    }

    fun updateFavoriteCityStatus() {
        _isCityFavoriteSelected.value = favoriteCityProvider.isCityFavorite(city!!)
    }

    // Click Event
    fun onCitySelect() {
        _isCitySelected.value = true
        _isCitySelected.value = false
    }

    fun onCityWebPageSelect() {
        _isCityWebPageSelected.value = true
        _isCityWebPageSelected.value = false
    }

    fun onDateSelect() {
        _isDateSelected.value = true
        _isDateSelected.value = false
    }

    fun onToggleFavoriteCity() {
        _isCityFavoriteSelected.value?.let { event: Boolean ->
            if (event) {
                favoriteCityProvider.delCity(city)
            } else {
                favoriteCityProvider.addCity(city)
            }
            updateFavoriteCityStatus()
        }
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("--- WeatherViewModel cleared!")
    }
}