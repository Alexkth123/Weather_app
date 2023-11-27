package com.alex.weather_app.data


import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Serializable
data class WeatherApiResponse(
    val approvedTime: String,
    val referenceTime: String,
    val geometry: Geometry,
    val timeSeries: List<TimeSeries>
)

@Serializable
data class Geometry(
    val type: String,
    val coordinates: List<List<Double>>
)

@Serializable
data class TimeSeries(
    val validTime: String,
    val parameters: List<Parameter>
)

@Serializable
data class Parameter(
    val name: String,
    val levelType: String,
    val level: Int,
    val unit: String,
    val values: List<Double>
)

/*
// Assume `jsonString` is your JSON data as a String
 val weatherData = Json.decodeFromString<WeatherApiResponse>(jsonString)

// To get the temperature, you would find the parameter with the name "t"
val temperature = weatherData.timeSeries.first().parameters
    .firstOrNull { it.name == "t" }?.values?.firstOrNull()

// Proceed similarly for rain, clouds (likely "tcc_mean" for total cloud cover), etc.



 */
