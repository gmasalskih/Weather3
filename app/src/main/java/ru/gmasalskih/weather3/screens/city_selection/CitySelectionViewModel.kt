package ru.gmasalskih.weather3.screens.city_selection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.*
import ru.gmasalskih.weather3.data.City
import ru.gmasalskih.weather3.data.storege.ICityProvider
import ru.gmasalskih.weather3.data.entity.geocoder.FeatureMember
import ru.gmasalskih.weather3.data.GeocoderApi
import ru.gmasalskih.weather3.data.entity.geocoder.BaseGeocoderEntity
import ru.gmasalskih.weather3.data.storege.local.LocalCityProvider
import timber.log.Timber

class CitySelectionViewModel : ViewModel() {

    private val cityProvider: ICityProvider = LocalCityProvider

    private val _isCityConfirmed = MutableLiveData<Boolean>()
    val isCityConfirmed: LiveData<Boolean>
        get() = _isCityConfirmed

    private val _responseListCity = MutableLiveData<List<City>>()
    val responseListCity: LiveData<List<City>>
        get() = _responseListCity

    init {
        Timber.i("--- CitySelectionViewModel created!")
        getResponse("")
    }

    fun onCityConfirm() {
        _isCityConfirmed.value = true
        _isCityConfirmed.value = false
    }

    fun getResponse(cityName: String) {
        Timber.i("--- $cityName")
        GeocoderApi.apiService.getCoordinate(cityName).enqueue(object : Callback<BaseGeocoderEntity> {
            override fun onFailure(call: Call<BaseGeocoderEntity>, t: Throwable) {
                Timber.i("--- ${t.message}")
            }

            override fun onResponse(
                call: Call<BaseGeocoderEntity>,
                response: Response<BaseGeocoderEntity>
            ) {
                val result: BaseGeocoderEntity? = response.body()
                result?.let {
                    val listFeatureMember = it.response.geoObjectCollection.featureMember
                    if (listFeatureMember.isNotEmpty()) {
                        _responseListCity.value = listFeatureMember.map {
                            City(
                                addressLine = getAddressLine(it),
                                countryName = getCountryName(it),
                                countyCode = getCountyCode(it),
                                name = getName(it),
                                lat = getLat(it),
                                lon = getLon(it)
                            )
                        }.toList()
                    }
                }
            }
        })
    }

    private fun getAddressLine(fm: FeatureMember): String {
        return fm.geoObject.metaDataProperty.geocoderMetaData.text
    }

    private fun getCountryName(fm: FeatureMember): String {
        return fm.geoObject.metaDataProperty.geocoderMetaData.addressDetails.country.countryName
    }

    private fun getCountyCode(fm: FeatureMember): String {
        return fm.geoObject.metaDataProperty.geocoderMetaData.addressDetails.country.countryNameCode
    }

    private fun getName(fm: FeatureMember): String {
        return fm.geoObject.name
    }

    private fun getLat(fm: FeatureMember): Float {
        return fm.geoObject.point.pos.split(" ")[1].toFloat()
    }

    private fun getLon(fm: FeatureMember): Float {
        return fm.geoObject.point.pos.split(" ")[0].toFloat()
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("--- CitySelectionViewModel cleared!")
    }
}