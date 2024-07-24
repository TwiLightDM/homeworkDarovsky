package com.darovsky.lesson6.data.api

import com.darovsky.lesson6.data.model.GeneralWeatherDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiGet {

    @GET("data/2.5/weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") appid: String,
    ): GeneralWeatherDto

//    @GET("/geo/1.0/")
//    suspend fun getCoordinates(
//        @Query("zip") zip: String,
//        @Query("appid") appid: String
//    ): CoordinatesDto

}