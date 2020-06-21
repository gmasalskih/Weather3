package ru.gmasalskih.weather3.data.storege.internet

import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import okhttp3.logging.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import ru.gmasalskih.weather3.data.entity.Location
import ru.gmasalskih.weather3.data.entity.Weather
import ru.gmasalskih.weather3.data.entity.weather.BaseWeatherEntity
import ru.gmasalskih.weather3.utils.TAG_LOG
import ru.gmasalskih.weather3.utils.toCoordinate
import timber.log.Timber

private const val BASE_URL_WEATHER = "https://api.weather.yandex.ru/v1/"
private const val KEY_WEATHER = "47461622-a45f-41f2-8167-efcf64e67b66"

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

private val APIWeather = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
    .baseUrl(BASE_URL_WEATHER)
    .client(httpClient.build())
    .build()

interface WeatherApiService {
    @Headers("X-Yandex-API-Key: $KEY_WEATHER")
    @GET("informers?lang=ru_RU")
    fun getWeather(@Query("lat") lat: String, @Query("lon") lon: String): Maybe<BaseWeatherEntity>
}

object WeatherApi {
    private val apiService: WeatherApiService by lazy {
        APIWeather.create(WeatherApiService::class.java)
    }

    fun getResponse(lon: String, lat: String): Maybe<Weather> {
        return apiService.getWeather(lon = lon.toCoordinate(), lat = lat.toCoordinate())
            .map {
                Weather(
                    temp = it.fact.temp,
                    timestamp = it.forecast.date,
                    windSpeed = it.fact.wind_speed.toInt(),
                    pressure = it.fact.pressure_mm,
                    humidity = it.fact.humidity,
                    icon = it.fact.icon,
                    url = it.info.url
                )
            }
    }

    fun getResponse(location: Location) = getResponse(lat = location.lat, lon = location.lon)
}