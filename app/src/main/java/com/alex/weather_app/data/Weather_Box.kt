package com.alex.weather_app.data

import kotlinx.serialization.Serializable

/**
 * This Class consists of the Weather datatype, and the enum weather_type to represent the
 * image of the weather condition
 */
@Serializable
class Weather_Box(
    val weatherDate: String,
    val weatherDay: WeatherDay,
    val weatherParams: WeatherParameters,
    val weatherType: WeatherType
){
    private fun get_weather_box():Weather_Box{
        return this
    }


}

/**
 *  Weather.kt would typically be a data model
 *  representing weather-related information.
 *  This model defines the data structure that your application will use across the UI,
 *  ViewModel, and Repository layers
 */

@Serializable
data class WeatherParameters(
    var temperature: String,
    var seaLevelPressure: String,
    var visibility: String,
    var windDirection: String,
    var windSpeed:String,
    var relativeHumidity: String,
    var thunderstormProbability: String,
    var totalCloudCover: String,
    var lowCloudCover: String,
    var mediumCloudCover: String,
    var highCloudCover: String,
    var windGust: String,
    var minPrecipitation: String,
    var maxPrecipitation: String,
    var percentOfPrecipitationInFrozenForm: String,
    var precipitationCategory: String,
    var meanPrecipitation: String,
    var medianPrecipitation: String
){}

@Serializable
enum class WeatherType(val emoji: weather_type, val wSymbol: Int) {
    CLEAR_SKY(weather_type.`☀️`, 1),
    NEARLY_CLEAR_SKY(weather_type.`🌤️`, 2),
    VARIABLE_CLOUDINESS(weather_type.`🌥️`, 3),
    HALF_CLEAR_SKY(weather_type.`🌥️`, 4),
    CLOUDY_SKY(weather_type.`☁️`, 5),
    OVERCAST(weather_type.`☁️`, 6),
    FOG(weather_type.`🌫️`, 7),
    LIGHT_RAIN_SHOWERS(weather_type.`️🌧️`, 8),
    MODERATE_RAIN_SHOWERS(weather_type.`️🌧️`, 9),
    HEAVY_RAIN_SHOWERS(weather_type.`️🌧️`, 10),
    THUNDERSTORM(weather_type.`️⛈️`, 11),
    LIGHT_SLEET_SHOWERS(weather_type.`️🌧️`, 12),
    MODERATE_SLEET_SHOWERS(weather_type.`️🌧️`, 13),
    HEAVY_SLEET_SHOWERS(weather_type.`️🌧️`, 14),
    LIGHT_SNOW_SHOWERS(weather_type.`️🌨️`, 15),
    MODERATE_SNOW_SHOWERS(weather_type.`️🌨️`, 16),
    HEAVY_SNOW_SHOWERS(weather_type.`️🌨️`, 17),
    LIGHT_RAIN(weather_type.`️🌧️`, 18),
    MODERATE_RAIN(weather_type.`️🌧️`, 19),
    HEAVY_RAIN(weather_type.`️🌧️`, 20),
    THUNDER(weather_type.`️⛈️`, 21),
    LIGHT_SLEET(weather_type.`☁️`, 22),
    MODERATE_SLEET(weather_type.`☁️`, 23),
    HEAVY_SLEET(weather_type.`️💀`, 24),
    LIGHT_SNOWFALL(weather_type.`️🌨️`, 25),
    MODERATE_SNOWFALL(weather_type.`️🌨️`, 26),
    HEAVY_SNOWFALL(weather_type.`️🌨️`, 27);

    companion object {
        fun getByWSymbol(wSymbol: Int): WeatherType? {
            return values().find { it.wSymbol == wSymbol }
        }
    }
}

@Serializable
enum class WeatherDay(val int: Int) {
    MONDAY(0),
    TUESDAY(1),
    WEDNESDAY(2),
    THURSDAY(3),
    FRIDAY(4),
    SATURDAY(5),
    SUNDAY(6);

    companion object {
        fun getByInt(value: Int): WeatherDay? {
            return values().find { it.int == value }
        }
    }
}

@Serializable
enum class weather_type(val emoji: String){
    `☀️`("Sunny"),
    `🌤️`("NearClear"),
    `🌥️`("HalfClear"),
    `☁️`("Cloudy"),
    `️🌧️`("Rainy"),
    `️❄️`("Cold"),
    `️⛈️`("Thunder"),
    `️🌨️`("Snowy"),
    `🌫️`("Foggy"),
    `️💀`("Ded")
}


fun int_to_day_string(int: Int): WeatherDay{

    when (int){
        0 -> return WeatherDay.MONDAY
        1 -> return WeatherDay.TUESDAY
        2 -> return WeatherDay.WEDNESDAY
        3 -> return WeatherDay.THURSDAY
        4 -> return WeatherDay.FRIDAY
        5 -> return WeatherDay.SATURDAY
        6 -> return WeatherDay.SUNDAY
    }
    return WeatherDay.MONDAY
}



