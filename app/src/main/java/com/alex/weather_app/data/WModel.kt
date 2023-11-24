package com.alex.weather_app.data

/**
 *  Weather.kt would typically be a data model
 *  representing weather-related information.
 *  This model defines the data structure that your application will use across the UI,
 *  ViewModel, and Repository layers
 */

data class Weather(
    val temperature: Double,
    val humidity: Int,
    val description: String,
    // ... other weather-related properties
)


enum class weather_type{
    FULL_ClOUD,
    HALF_CLOUD,
    SUNNY,
    RAIN

}

fun Weather.toWeather(): Weather {
    return Weather(
        temperature = this.temperature,
        humidity = this.humidity,
        description = this.description
    )
}