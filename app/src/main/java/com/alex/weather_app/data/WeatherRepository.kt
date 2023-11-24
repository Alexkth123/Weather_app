package com.alex.weather_app.data

//import com.alex.weather_application.data.remote.WeatherApiService
//import com.alex.weather_application.data.local.database.WeatherDao
//import com.alex.weather_application.data.models.Weather
import kotlinx.coroutines.flow.Flow
import java.util.prefs.Preferences


/**
 * The following code is GPT generated and serves the purpus to give an example how the class can work
 * @WeatherRepository Class is responable to communicate with the APIS and fetch the data
 *
 * Functions :
 *
 * get Location
 * Get weather data
 * Get Weather Update
 * Get local weather
 */

class WeatherRepository(

   // private val apiService: WeatherApiService,
   // private val weatherDao: WeatherDao
) {




    // Fetches weather data from a remote source
    suspend fun getWeatherData(location: String): Weather {
        val response = Weather(+32.1,10,"Clear and blue sky in cali")//apiService.getWeather(location)
        // Here you could also cache the response in a local database if needed
      //  weatherDao.insertWeather(response.toWeatherEntity())
        return  response.toWeather()
    }

    /*

    // Gets cached weather data from the local database
    fun getLocalWeatherData(): Flow<Weather> {
        return weatherDao.getWeather().map { it.toWeather() }
    }

    // Example of a function that refreshes local data with remote data
    suspend fun refreshWeatherData(location: String) {
        val remoteWeather = getWeatherData(location)
        weatherDao.updateWeather(remoteWeather.toWeatherEntity())
    }

    // ... Other repository methods as needed

     */



}