package com.darovsky.lesson6.data.model

data class GeneralWeatherDto(
    val main: MainDto,
    val wind: WindDto,
    val weather: List<WeatherDto>,
    val visibility: String,
    val name: String,
)