package ru.gmasalskih.weather3.data.storege.internet

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import okhttp3.logging.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.gmasalskih.weather3.data.entity.Coordinates
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.data.entity.geocoder.BaseGeocoderEntity
import ru.gmasalskih.weather3.data.entity.geocoder.GeoObject
import ru.gmasalskih.weather3.utils.EMPTY_COORDINATE
import ru.gmasalskih.weather3.utils.TAG_LOG
import ru.gmasalskih.weather3.utils.toCoordinate
import timber.log.Timber

private const val BASE_URL_GEOCODER = "https://geocode-maps.yandex.ru/1.x/"
private const val KEY_GEOCODER = "1d864ec2-c76a-4c6a-b324-a76756a06882"

private val log = object : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Timber.i("$TAG_LOG $message")
    }
}

private val logging = HttpLoggingInterceptor(log).apply {
    setLevel(HttpLoggingInterceptor.Level.BODY)
}
private val httpClient = OkHttpClient.Builder().apply {
    addInterceptor(logging)
}

private val APIGeocoder = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
    .baseUrl(BASE_URL_GEOCODER)
    .client(httpClient.build())
    .build()

interface GeocoderApiService {
    @GET("?apikey=$KEY_GEOCODER&format=json&results=100&kind=locality")
    fun getGeocoderEntity(@Query("geocode") geocode: String): Maybe<BaseGeocoderEntity>
}

object GeocoderApi {
    private val apiService: GeocoderApiService by lazy {
        APIGeocoder.create(GeocoderApiService::class.java)
    }

    fun getListLocations(geocode: String): Maybe<List<Location>> {
        return apiService.getGeocoderEntity(geocode)
            .map {
                it.response?.geoObjectCollection?.featureMember
            }.flatMapObservable {
                Observable.fromIterable(it)
            }.map {
                it.geoObject
            }.filter {
                getLat(it) != EMPTY_COORDINATE && getLon(it) != EMPTY_COORDINATE
            }.map {
                Location(
                    addressLine = getAddressLine(it),
                    countryName = getCountryName(it),
                    countyCode = getCountyCode(it),
                    name = getName(it),
                    lat = getLat(it),
                    lon = getLon(it)
                )
            }.toList()
            .filter { !it.isNullOrEmpty() }
    }

    fun getLocation(coordinates: Coordinates): Maybe<Location> =
        getListLocations("${coordinates.lon.toCoordinate()},${coordinates.lat.toCoordinate()}")
            .flatMapObservable { Observable.fromIterable(it) }
            .firstElement()

    private fun getAddressLine(geoObject: GeoObject): String {
        return geoObject.metaDataProperty?.geocoderMetaData?.text ?: ""
    }

    private fun getCountryName(geoObject: GeoObject): String {
        return geoObject.metaDataProperty?.geocoderMetaData?.addressDetails?.country?.countryName
            ?: ""
    }

    private fun getCountyCode(geoObject: GeoObject): String {
        return geoObject.metaDataProperty?.geocoderMetaData?.addressDetails?.country?.countryNameCode
            ?: ""
    }

    private fun getName(geoObject: GeoObject): String {
        return geoObject.name ?: ""
    }

    private fun getLat(geoObject: GeoObject): String {
        return geoObject.point?.pos?.let {
            it.split(" ")[1].toCoordinate()
        } ?: EMPTY_COORDINATE
    }

    private fun getLon(geoObject: GeoObject): String {
        return geoObject.point?.pos?.let {
            it.split(" ")[0].toCoordinate()
        } ?: EMPTY_COORDINATE
    }
}
