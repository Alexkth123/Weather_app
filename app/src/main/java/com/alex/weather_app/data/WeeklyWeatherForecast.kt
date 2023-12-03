package com.alex.weather_app.data

import android.util.Log
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


/**
 * This class is soupose to be an array of the weatherboxes,
 * meaning it will contain the forecast for the 7 coming days. Each day is represented
 * as a Weather_Box.
 *
 * The idea is then to create an instance of this object in the viewmodel to display the forecast.
 *
 *When calling the constructor of forcast
 */

@Serializable
class WeeklyWeatherForecast {

    private val weeklyForecast: ArrayList<DailyWeatherForecast> = ArrayList()

    init {
        for (i in 0 until 7) {
            Log.d("Weekly forecast", "Creating weeklyForecast $i")
            weeklyForecast.add(DailyWeatherForecast())
        }
    }

    fun setDailyForecast(day: WeatherDay, dailyForecast: DailyWeatherForecast) {
        weeklyForecast.set(day.int, dailyForecast)
    }

    fun getDailyForecast(day: WeatherDay): DailyWeatherForecast {
        return weeklyForecast.get(day.int)
    }

    /**
     * Serialize the WeeklyWeatherForecast to a JSON string.
     */
    fun toJson(): String {
        return Json.encodeToString(this)
    }

    companion object {
        /**
         * Deserialize a WeeklyWeatherForecast from a JSON string.
         */
        fun fromJson(json: String): WeeklyWeatherForecast {
            return Json.decodeFromString(json)
        }
    }
}
