package com.darovsky.lesson6

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.darovsky.lesson6.data.api.ApiGet
import com.darovsky.lesson6.data.viewModel.GeneralWeather
import com.darovsky.lesson6.databinding.ActivityMainBinding
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    companion object {
        private const val READ_TIMEOUT_IN_SECONDS = 30L
        private const val CONNECTION_TIMEOUT_IN_SECONDS = 30L
        private const val DEV_BASE_URL = "http://api.openweathermap.org"
    }

    private val moshiBuilder = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(CONNECTION_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(logger)
            .build()


        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(DEV_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshiBuilder))
            .build()

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        coroutineScope.launch {
            val response = retrofit.create(ApiGet::class.java)
                .getWeather("Saratov", "99e5ce78108661718aa353ec4ef94088")
            withContext(Dispatchers.Main) {
                val direction = when (response.wind.deg) {
                    in 1..44 -> "N"
                    in 45..89 -> "NE"
                    in 90..134 -> "E"
                    in 135..179 -> "SE"
                    in 180..224 -> "S"
                    in 225..269 -> "SW"
                    in 270..314 -> "W"
                    in 315..359 -> "NW"
                    else -> "N"
                }

                val generalWeather = GeneralWeather(
                    response.name,
                    (response.main.temp - 273.15).toInt().toString() + "°C",
                    "Feels like " + (response.main.feels_like - 273.15).toInt()
                        .toString() + "°C, " + response.weather[0].description,
                    "min: " + (response.main.temp_min - 273.15).toInt()
                        .toString() + "°C, max: " + (response.main.temp_max - 273.15).toInt()
                        .toString() + "°C",
                    "pressure: " + response.main.pressure.toString() + " hPa",
                    "humidity: " + response.main.humidity.toString() + "%",
                    "visibility: " + response.visibility + " km\"",
                    "wind speed: " + response.wind.speed.toString() + " m/s",
                    "gust: " + response.wind.gust.toString() + " m/s",
                    "direction: $direction"
                )

                binding.generalWeather = generalWeather
            }
        }
    }
}