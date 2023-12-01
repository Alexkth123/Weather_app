package com.alex.weather_app.data

import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.alex.weather_app.data.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

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
 *
 *
 */


class WModel(private val repository: WeatherRepository){
    val weeklyForecast = WeeklyWeatherForecast()


     init {
     //val _parsed_data :MutableStateFlow<WeatherApiResponse>
 }


    suspend fun make_weather_Box(location: String):WeeklyWeatherForecast{
        // more relevant name "makeForecast"


        var _data :WeatherApiResponse =repository.fetchWeatherData(location)
        Log.d("API response","${_data.toString()}")
        Log.d("API response","${_data.timeSeries[1].parameters[10].toString()}")
        // timeSeris[x]= day
        // parameter[10]=temp
        // parameter[15]=rain %
        // parameter[14]=WindSpeed


        for (i in 0 until 7) {

            // temp>0
            // temp >0 + rain
            val temp = _data.timeSeries[i].parameters[10].values.get(0)
            val ranin= _data.timeSeries[i].parameters[15].values.get(0)
            var  weatherType = weather_type.`☀️`

            when {
                temp > 0.0&&ranin >= 20 -> weatherType=weather_type.`☀️`
                temp < 0.0&&ranin >= 20 -> weatherType=weather_type.`️❄️`
                temp > 0.0&&ranin >= 40 -> weatherType=weather_type.`️🌧️`

                else -> weatherType = weather_type.`️💀`
            }


            val weatherBox = Weather_Box(
                Weather(_data.timeSeries[i].parameters[10].values.toString(),
                _data.timeSeries[i].parameters[15].values.toString(),
                _data.timeSeries[i].parameters[10].values.toString(),
                _data.timeSeries[i].parameters[14].values.toString(),
                "Main stream description"),weatherType ) //weather_type.SUNNY

            weeklyForecast.setWeatherBoxForDay(i, weatherBox)
        }

        return weeklyForecast



    }

}