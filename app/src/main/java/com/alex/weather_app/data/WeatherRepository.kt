package com.alex.weather_app.data
import android.util.Log
import com.alex.weather_app.exeptions.ApiException
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
//import com.alex.weather_application.data.remote.WeatherApiService
//import com.alex.weather_application.data.local.database.WeatherDao
//import com.alex.weather_application.data.models.Weather
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor

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

interface ApiService {

    @GET("weather/forecast?lonLat=lon/14.333/lat/60.383")
    //suspend fun getWeatherData(@Query("q") location: String): Response<WeatherApiResponse>
    // The Query parameter is for adding the location cordinates at the end of the link
    suspend fun getWeatherData(): Response<WeatherApiResponse>
}

class WeatherRepository() {



    private val apiService: ApiService
    private val BASE_URL = "https://maceo.sth.kth.se/"
    //private var weatherData = parseJsonToWeatherData("hello") // make to mutable stateflow
   // private val weatherDao: WeatherDao

    init {
        val mHttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val mOkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(mHttpLoggingInterceptor)
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }



    suspend fun fetchWeatherData(location: String): WeatherApiResponse {
        Log.d("API :", "entered fetchWeatherData")

        val response = apiService.getWeatherData()
        if (response.isSuccessful) {
            // Assuming you have a method to save data to the database
            Log.d("API call:", "Successful")

            return response.body() ?: throw IllegalStateException("Received null response body")
        } else {
            // Handle API error
            Log.d("API call:", "Fail")
            throw ApiException("Failed to fetch weather data: ${response.errorBody()?.string()}")
        }
    }


    fun saveWeatherDataFromJson(jsonString: String) {
       val weatherData = parseJsonToWeatherData(jsonString)
        // Save `weatherData` to a database or some form of storage
    }

     fun parseJsonToWeatherData(jsonString: String): WeatherApiResponse {
        val gson = Gson()
        return gson.fromJson(jsonString, WeatherApiResponse::class.java)
    }




    // Fetches weather data from a remote source

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