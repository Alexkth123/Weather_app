package com.alex.weather_app.data

import android.util.Log
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

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

    suspend fun buildWeeklyForecast(location: String):WeeklyWeatherForecast{
        // more relevant name "makeForecast"


        var json_data : WeatherApiResponse = repository.fetchWeatherData(location)
        Log.d("API response","${json_data.toString()}")
        Log.d("API response","${json_data.timeSeries[1].parameters[10].toString()}")

        val days = HashSet<String>()
        for (ts in json_data.timeSeries){
            val date = ts.validTime.substring(0, 10)

            days.add(date)


            if (days.size <= 7){
                val time = ts.validTime.substring(11, 19)
                val day = getDayFromDate(date)
                val hour = time.substring(0,2)

                var temperature = ""
                var seaLevelPressure = ""
                var visibility = ""
                var windDirection = ""
                var windSpeed = ""
                var relativeHumidity = ""
                var thunderstormProbability = ""
                var totalCloudCover = ""
                var lowCloudCover = ""
                var mediumCloudCover = ""
                var highCloudCover = ""
                var windGust = ""
                var minPrecipitation = ""
                var maxPrecipitation = ""
                var percentOfPrecipitationInFrozenForm = ""
                var precipitationCategory = ""
                var meanPrecipitation = ""
                var medianPrecipitation = ""

                var weatherType = WeatherType.CLEAR_SKY

                for (param in ts.parameters){
                    when (param.name){
                        "t" -> {
                            temperature = param.values[0].toString()
                            Log.d("API response","t ${temperature}")
                        }
                        "vis" -> visibility = param.values[0].toString()
                        "wd" -> windDirection = param.values[0].toString()
                        "ws" -> windSpeed = param.values[0].toString()
                        "r" -> relativeHumidity = param.values[0].toString()
                        "tstm" -> thunderstormProbability = param.values[0].toString()
                        "tcc_mean" -> totalCloudCover = param.values[0].toString()
                        "lcc_mean" -> lowCloudCover = param.values[0].toString()
                        "mcc_mean" -> mediumCloudCover = param.values[0].toString()
                        "hcc_mean" -> highCloudCover = param.values[0].toString()
                        "gust" -> windGust = param.values[0].toString()
                        "pmin" -> minPrecipitation = param.values[0].toString()
                        "pmax" -> maxPrecipitation = param.values[0].toString()
                        "pcat" -> precipitationCategory = param.values[0].toString()
                        "pmean" -> meanPrecipitation = param.values[0].toString()
                        "pmedian" -> {
                            medianPrecipitation = param.values[0].toString()
                            Log.d("API response","pmedian ${medianPrecipitation}")
                        }
                        "msl" -> {
                            seaLevelPressure = param.values[0].toString()
                            Log.d("API response","msl ${seaLevelPressure}")
                        }
                        "spp" -> {
                            percentOfPrecipitationInFrozenForm = param.values[0].toString()
                            Log.d("API response","spp ${percentOfPrecipitationInFrozenForm}")
                        }
                        "Wsymb2" -> {
                            weatherType = WeatherType.getByWSymbol(param.values[0].toInt())!!
                            Log.d("API response","Wsymb2: ${weatherType}")
                        }
                    }
                }

                val weatherParameters = WeatherParameters(temperature, seaLevelPressure, visibility, windDirection, windSpeed, relativeHumidity, thunderstormProbability, totalCloudCover, lowCloudCover, mediumCloudCover, highCloudCover, windGust, minPrecipitation, maxPrecipitation, percentOfPrecipitationInFrozenForm, precipitationCategory, meanPrecipitation, medianPrecipitation)

                val weatherBox = Weather_Box(date, day, hour.toInt(), weatherParameters, weatherType)

                if(weeklyForecast.getDailyForecast(day) == null){
                    val dailyForecast = DailyWeatherForecast()

                    dailyForecast.setHourlyForecast(hour.toInt(), weatherBox)

                    weeklyForecast.setDailyForecast(day, dailyForecast)
                }else{
                    val dailyForecast = weeklyForecast.getDailyForecast(day)

                    if (dailyForecast != null) {
                        dailyForecast.setHourlyForecast(hour.toInt(), weatherBox)

                        weeklyForecast.setDailyForecast(day, dailyForecast)
                    }
                }
            }
        }

        return weeklyForecast

    }

    private fun getDayFromDate(dateString: String) : WeatherDay{
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        try {
            // Parse the date string
            val date = dateFormat.parse(dateString)

            // Format the parsed date to get the day of the week
            val dayOfWeek = SimpleDateFormat("EEEE", Locale.getDefault()).format(date)

            return dayOfWeekToEnum(dayOfWeek)
        } catch (e: Exception) {
            // Handle parsing errors
            e.printStackTrace()
            return WeatherDay.MONDAY
        }
    }


    fun getTodayDay(): WeatherDay {
        val today = LocalDate.now()
        val dayOfWeek = today.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
        return dayOfWeekToEnum(dayOfWeek)
    }

    private fun dayOfWeekToEnum(dayOfWeek: String): WeatherDay {
        return when (dayOfWeek.lowercase(Locale.ROOT)) {
            "sunday" -> WeatherDay.SUNDAY
            "monday" -> WeatherDay.MONDAY
            "tuesday" -> WeatherDay.TUESDAY
            "wednesday" -> WeatherDay.WEDNESDAY
            "thursday" -> WeatherDay.THURSDAY
            "friday" -> WeatherDay.FRIDAY
            "saturday" -> WeatherDay.SATURDAY
            else -> {WeatherDay.MONDAY}
        }
        return WeatherDay.MONDAY
    }
}