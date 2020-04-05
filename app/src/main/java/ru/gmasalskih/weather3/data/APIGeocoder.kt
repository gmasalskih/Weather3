package ru.gmasalskih.weather3.data

import okhttp3.*
import okhttp3.logging.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import ru.gmasalskih.weather3.data.entity.geocoder.BaseGeocoderEntity
import timber.log.Timber

private const val BASE_URL_GEOCODER = "https://geocode-maps.yandex.ru/1.x/"
private const val KEY_GEOCODER = "1d864ec2-c76a-4c6a-b324-a76756a06882"

private val log =object: HttpLoggingInterceptor.Logger{
    override fun log(message: String) {
        Timber.i("--- $message")
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
    fun getCoordinate(@Query("geocode") cityName: String): Call<BaseGeocoderEntity>
}

object GeocoderApi{
    val apiService: GeocoderApiService by lazy {
        APIGeocoder.create(GeocoderApiService::class.java)
    }
}