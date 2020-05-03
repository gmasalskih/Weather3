package ru.gmasalskih.weather3.api

import okhttp3.*
import okhttp3.logging.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import ru.gmasalskih.weather3.data.entity.Weather
import ru.gmasalskih.weather3.data.entity.weather.BaseWeatherEntity
import ru.gmasalskih.weather3.utils.TAG_LOG
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
    .baseUrl(BASE_URL_WEATHER)
    .client(httpClient.build())
    .build()

interface WeatherApiService {
    @Headers("X-Yandex-API-Key: $KEY_WEATHER")
    @GET("informers?lang=ru_RU")
    fun getWeather(@Query("lat") lat: Float, @Query("lon") lon: Float): Call<BaseWeatherEntity>
}

object WeatherApi {
    private val apiService: WeatherApiService by lazy {
        APIWeather.create(WeatherApiService::class.java)
    }

    fun getResponse(lon: Float, lat: Float, callback: (Weather) -> Unit) {
        apiService.getWeather(lon = lon, lat = lat).enqueue(object :
            Callback<BaseWeatherEntity> {
            override fun onFailure(call: Call<BaseWeatherEntity>, t: Throwable) {
                Timber.i("$TAG_LOG ${t.message}")
            }

            override fun onResponse(
                call: Call<BaseWeatherEntity>,
                response: Response<BaseWeatherEntity>
            ) {
                val body = response.body()
                if (body != null && response.isSuccessful) {
                    body.apply {
                        val weather = Weather(
                            temp = fact.temp,
                            timestamp = now_dt,
                            windSpeed = fact.wind_speed.toInt(),
                            pressure = fact.pressure_mm,
                            humidity = fact.humidity,
                            icon = fact.icon,
                            url = info.url
                        )
                        callback(weather)
                    }
                } else {
                    // что-то пошло не по плану
                    Timber.i("$TAG_LOG Код ответа сервера: ${response.code()} Данные с сервера ${response.raw()}")
                }
            }
        })
    }
}