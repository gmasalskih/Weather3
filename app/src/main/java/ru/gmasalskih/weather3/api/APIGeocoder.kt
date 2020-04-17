package ru.gmasalskih.weather3.api

import okhttp3.*
import okhttp3.logging.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.gmasalskih.weather3.data.entity.City
import ru.gmasalskih.weather3.data.entity.geocoder.BaseGeocoderEntity
import ru.gmasalskih.weather3.data.entity.geocoder.FeatureMember
import ru.gmasalskih.weather3.data.entity.geocoder.GeoObject
import ru.gmasalskih.weather3.utils.TAG_LOG
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
    .baseUrl(BASE_URL_GEOCODER)
    .client(httpClient.build())
    .build()

interface GeocoderApiService {
    @GET("?apikey=$KEY_GEOCODER&format=json&results=100")
    fun getGeocoderEntity(@Query("geocode") geocode: String): Call<BaseGeocoderEntity?>
}

object GeocoderApi {
    private val apiService: GeocoderApiService by lazy {
        APIGeocoder.create(GeocoderApiService::class.java)
    }
    fun getResponse(geocode: String, callback: (List<City>) -> Unit) {
        apiService.getGeocoderEntity(geocode)
            .enqueue(object : Callback<BaseGeocoderEntity?> {
                override fun onFailure(call: Call<BaseGeocoderEntity?>, t: Throwable) {
                    Timber.i("$TAG_LOG ${t.message}")
                }
                override fun onResponse(
                    call: Call<BaseGeocoderEntity?>,
                    response: Response<BaseGeocoderEntity?>
                ) {
                    response.body()?.response?.geoObjectCollection?.featureMember?.let { listFM: List<FeatureMember> ->
                        val list = listFM.mapNotNull { fm: FeatureMember ->
                            fm.geoObject
                        }.map { geoObject: GeoObject ->
                            City(
                                addressLine = getAddressLine(geoObject),
                                countryName = getCountryName(geoObject),
                                countyCode = getCountyCode(geoObject),
                                name = getName(geoObject),
                                lat = getLat(geoObject),
                                lon = getLon(geoObject)
                            )
                        }.toList()
                        callback(list)
                    }
                }
            })
    }

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

    private fun getLat(geoObject: GeoObject): Float {
        return geoObject.point?.pos?.let {
            it.split(" ")[1].toFloat()
        } ?: 0.0F
    }

    private fun getLon(geoObject: GeoObject): Float {
        return geoObject.point?.pos?.let {
            it.split(" ")[0].toFloat()
        } ?: 0.0F
    }
}