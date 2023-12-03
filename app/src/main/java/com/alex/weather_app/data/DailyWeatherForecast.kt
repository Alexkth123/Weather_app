package com.alex.weather_app.data

import android.util.Log

class DailyWeatherForecast {
    private val dailyForecast: ArrayList<Weather_Box?> = ArrayList()

    constructor(){
        for (i in 0 until 24){
            Log.d("Daily Forecast","Creating dailyforecast $i")
            dailyForecast.add(i, null)
        }
    }

    fun setHourlyForecast(hour: Int, weatherBox: Weather_Box){
        dailyForecast.set(hour, weatherBox)
    }

    fun getHourlyForecast(hour: Int): Weather_Box? {
        return dailyForecast.get(hour)
    }
}
