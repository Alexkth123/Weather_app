package com.alex.weather_app.data

/**
 * This Class consists of the Weather datatype, and the enum weather_type to represent the
 * image of the weather condition
 */
class Weather_Box (
   val weather: Weather,
   val weatherType: weather_type
){


    private fun get_weather_box():Weather_Box{
        return this
    }
}



/**
 *  Weather.kt would typically be a data model
 *  representing weather-related information.
 *  This model defines the data structure that your application will use across the UI,
 *  ViewModel, and Repository layers
 */

data class Weather(
    val temperature: String,
    val rain: String,
    val cloud_covrage: String,
    val wind_speed:String,
    val description: String,
    // ... other weather-related properties
){

    override fun toString(): String {
        val str = "Temp ="+ temperature

        return str
    }
}


enum class weather_type{
    FULL_ClOUD,
    HALF_CLOUD,
    SUNNY,
    RAIN,
    `☀️`,
    `🌥️`,
    `️🌧️`,
    `️❄️`,
    `️💀`


}


// Maybe not useful since every value is a string
fun Weather.toWeather(): Weather {
    return Weather(
        temperature = this.temperature,
        cloud_covrage = this.cloud_covrage,
        rain= this.rain,
        wind_speed=this.wind_speed,
        description = this.description
    )
}



