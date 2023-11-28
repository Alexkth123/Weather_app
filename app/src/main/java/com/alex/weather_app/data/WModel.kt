package com.alex.weather_app.data

import android.util.Log
import com.alex.weather_app.data.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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


/**
 * In this class i want to take in all the json data, make a 7-day forcast
 *
 * The idea is that the WModel is handeling all the weather data and contains all the logic.
 * Later we can just create an instance of the WModel in the WeatherVM and view the forcasts.
 *
 * Is it resonable to make a n
 *
 * Init:
 * fetch data
 *
 *
 * Functions:
 * get_forcast
 *
 */

class WModel(private val repository: WeatherRepository){

   // val _parsed_data :MutableStateFlow<WeatherApiResponse> I want to save it to the system


     init {

     //val _parsed_data :MutableStateFlow<WeatherApiResponse>


 }

    suspend fun make_weather_Box(){
        var _raw_data =repository.fetchWeatherData("Cali")
        Log.d("API response","${_raw_data.toString()}")

        //Log.d("API response","DAY 2: ${_raw_data.timeSeries[1].parameters.toString()}")



       // var _parsed_data = repository.parseJsonToWeatherData("Raw_api_Data")
     //   Log.d("Parsed Data","$_parsed_data")

        // try calling and looking at the parsed data

    }





}