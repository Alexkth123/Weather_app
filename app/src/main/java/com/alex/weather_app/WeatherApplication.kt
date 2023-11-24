package com.alex.weather_app

import android.app.Application
import android.content.Context
import com.alex.weather_app.data.WeatherRepository
import com.alex.weather_app.ui.viewmodels.WeatherViewModel
import java.util.prefs.Preferences



/**
 * Initialization of your WeatherRepository,
 * which might involve setting up a database instance, network clients, etc.
 *
 *
 * Configuration of any application-wide libraries or frameworks, such as starting analytics,
 * crash reporting tools, or dependency injection frameworks like Hilt.
 *
 *
 * Optionally, setup of any shared preferences or other
 * persistent storage that needs to be accessible application-wide
 */


private const val APP_PREFERENCES_NAME = "Weather_app"


class WeatherApplication : Application() {
    lateinit var weatherRepository: WeatherRepository
    lateinit var vm : WeatherViewModel

    override fun onCreate() {
        super.onCreate()
        // Initialize the WeatherRepository
        weatherRepository = WeatherRepository()
        // Other initializations if needed
    }
}
