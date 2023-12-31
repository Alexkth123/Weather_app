package com.alex.weather_app.ui.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.alex.weather_app.data.Coordinates
import com.alex.weather_app.data.WModel
import com.alex.weather_app.data.WeatherDay
import com.alex.weather_app.data.WeatherParameters
import com.alex.weather_app.data.WeatherRepository
import com.alex.weather_app.data.WeatherType
import com.alex.weather_app.data.Weather_Box
import com.alex.weather_app.data.WeeklyWeatherForecast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch


interface WeatherViewModel{
    val weeklyForecast: StateFlow<WeeklyWeatherForecast>
    val coordinates: StateFlow<Coordinates>
    val internetConnection : LiveData<Boolean>
    val secondsElapsed: LiveData<Int>
    val clickedWeatherBox: StateFlow<Weather_Box?>
     fun getWeather()
     fun newWeatherLocation()
     fun setClickedWeatherBox(weatherBox: Weather_Box?)
}


/**
 * Make a Object @weatherBox that consists of the Weather datatype and the defined picture,
 * is ready to be displayed by sending it to the
 *
 *
 * Make a list of
 */

class WeatherVM (
    private val weatherRepository: WeatherRepository,
    private val context: Context
):WeatherViewModel,ViewModel() {

    private val PREFS_NAME = "weather_prefs"
    private val KEY_SERIALIZED_DATA = "serialized_data"

    private var job: Job? = null
    private var timerJob: Job? = null

    private val _secondsElapsed = MutableLiveData<Int>(0)
    override val secondsElapsed: LiveData<Int>
        get() = _secondsElapsed



    //val weatherData: StateFlow<Weather?> = _weatherData.asStateFlow()
    private var _weeklyForecast = MutableStateFlow(WeeklyWeatherForecast())
    override val weeklyForecast: StateFlow<WeeklyWeatherForecast>
        get() = _weeklyForecast

    private var _coordinates = MutableStateFlow(Coordinates(14.333f, 60.383f))
    override val coordinates: StateFlow<Coordinates>
        get() = _coordinates

    private var _internetConnection =MutableLiveData(true)
    override val internetConnection: LiveData<Boolean>
        get() = _internetConnection

    private var _clickedWeatherBox = MutableStateFlow(Weather_Box(
        "",
        WeatherDay.MONDAY,
        WeatherParameters("", "", "","", "", "","", "", "","", "", "","", "", "","", "", ""),
        WeatherType.CLEAR_SKY
    ))
    override val clickedWeatherBox: StateFlow<Weather_Box?>
        get() = _clickedWeatherBox


    // make a new model for each new forcast created
    private val model= WModel(weatherRepository)

    private val SavedforcastList: Array<WeeklyWeatherForecast>  // Holds a list of created forcasts
        get() {
            TODO()
        }


    override fun getWeather(){
        //job?.cancel()

        // This is more appropriate as a update weather function
        Log.d("API","Calling model :model.make_weather_Box()  ")

        Log.d("API call to :", "${_coordinates.value.toString()}")


        var tmp= _secondsElapsed.value
        if (tmp != null) {
            if (tmp>300){

                job =viewModelScope.launch {
                    try {
                        //model.make_weather_Box(_coordinates.value.toString())
                        _weeklyForecast.value = model.buildWeeklyForecast(_coordinates.value.toString())

                        saveSerializedDataToStorage(_weeklyForecast.value.toJson())
                        Log.e("Serialize", "Serialized to storage")
                    }catch (exception: Exception) {
                        Log.e("API Error", "Failed to fetch weather data", exception)
                    }
                }

                Log.d("Timer","Setting timer to 0  ")
                onCleared()
                startTimer(0)

            }else{}
        }
    }

    override fun newWeatherLocation() {
        job = viewModelScope.launch {
            try {
                //model.make_weather_Box(_coordinates.value.toString())
                _weeklyForecast.value = model.buildWeeklyForecast(_coordinates.value.toString())

                // Save the updated forecast to storage after fetching new weather data
                saveSerializedDataToStorage(_weeklyForecast.value.toJson())
                Log.e("Serialize", "Serialized to storage")
            } catch (exception: Exception) {
                Log.e("API Error", "Failed to fetch weather data", exception)
            }
        }
    }

    override fun setClickedWeatherBox(weatherBox: Weather_Box?){
        if (weatherBox != null) {
            _clickedWeatherBox.value = weatherBox
        }else{
            _clickedWeatherBox.value = Weather_Box(
                "",
                WeatherDay.MONDAY,
                WeatherParameters("", "", "","", "", "","", "", "","", "", "","", "", "","", "", ""),
                WeatherType.CLEAR_SKY
            )
        }
    }

    companion object {
        // Factory outside the companion object
        fun createFactory(context: Context): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(WeatherVM::class.java)) {
                        @Suppress("UNCHECKED_CAST")
                        return WeatherVM(WeatherRepository(), context) as T
                    }
                    throw IllegalArgumentException("Unknown ViewModel class")
                }
            }
        }
    }

    private fun startTimer( int: Int) {
        timerJob = viewModelScope.launch(Dispatchers.Main) {
            var seconds = int
            while (isActive) {
                delay(1000)
                seconds++
                _secondsElapsed.value = seconds
                Log.d("Timer :", "${_secondsElapsed.value}")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }


    init {
        // Load serialized data from storage
        val serializedData: String? = loadSerializedDataFromStorage()

        // Deserialize the data and update the _weeklyForecast variable
        if (serializedData != null) {
            try {
                _weeklyForecast.value = WeeklyWeatherForecast.fromJson(serializedData)
                Log.e("WeatherVM", "Deserialized data!")
            } catch (e: Exception) {
                Log.e("WeatherVM", "Failed to deserialize data", e)
            }
        }

        // Start the timer
        startTimer(300)
    }

    private fun loadSerializedDataFromStorage(): String? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(KEY_SERIALIZED_DATA, null)
    }

    private fun saveSerializedDataToStorage(serializedData: String) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(KEY_SERIALIZED_DATA, serializedData).apply()
    }

}