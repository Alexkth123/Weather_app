package com.alex.weather_app.data

/**
 * This Class consists of the Weather datatype, and the enum weather_type to represent the
 * image of the weather condition
 */
class Weather_Box (
    weather: Weather,
    weatherType: weather_type
){
    private fun get_weather_box():Weather_Box{
        return this
    }
}