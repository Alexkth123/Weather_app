package com.alex.weather_app.data

import android.util.Log


/**
 * This class is soupose to be an array of the weatherboxes,
 * meaning it will contain the forecast for the 7 coming days. Each day is represented
 * as a Weather_Box.
 *
 * The idea is then to create an instance of this object in the viewmodel to display the forecast.
 *
 *When calling the constructor of forcast
 */
class WeeklyWeatherForecast {

    private val weeklyForecast: ArrayList<DailyWeatherForecast> = ArrayList()

    constructor(){
        for (i in 0 until 7) {
            Log.d("Weekly forecast","Creating weeklyForecast $i")
            weeklyForecast.add(DailyWeatherForecast())
        }
    }

    fun setDailyForecast(day: WeatherDay, dailyForecast: DailyWeatherForecast) {
        weeklyForecast.set(day.int, dailyForecast)
    }

    fun getDailyForecast(day: WeatherDay): DailyWeatherForecast {
        return weeklyForecast.get(day.int)
    }
}
