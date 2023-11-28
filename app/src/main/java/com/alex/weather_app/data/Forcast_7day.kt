package com.alex.weather_app.data

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow




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
    private val weeklyForecast: Array<Weather_Box?> = arrayOfNulls(7) // Array to hold 7 Weather_Box objects

    fun setWeatherBoxForDay(index: Int, weatherBox: Weather_Box) {
        if (index in 0..6) { // Check if index is within the range 0 to 6
            weeklyForecast[index] = weatherBox
        } else {
            throw IndexOutOfBoundsException("Index must be between 0 and 6")
        }
    }

    fun getWeatherBoxForDay(index: Int): Weather_Box? {
        if (index in 0..6) {
            return weeklyForecast[index]
        } else {
            throw IndexOutOfBoundsException("Index must be between 0 and 6")
        }
    }
}
