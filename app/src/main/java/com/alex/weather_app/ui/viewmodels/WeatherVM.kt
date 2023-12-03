package com.alex.weather_app.ui.viewmodels

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.alex.weather_app.WeatherApplication
import com.alex.weather_app.data.Coordinates
import com.alex.weather_app.data.WModel
import com.alex.weather_app.data.Weather
import com.alex.weather_app.data.WeatherRepository
import com.alex.weather_app.data.Weather_Box
import com.alex.weather_app.data.WeeklyWeatherForecast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds
import com.alex.weather_app.ui.theme.StyleBlue


interface WeatherViewModel{
    val weeklyForecast: StateFlow<WeeklyWeatherForecast>
    val coordinates: StateFlow<Coordinates>
    val internetConnection : LiveData<Boolean>
    val secondsElapsed: LiveData<Int>
     fun getWeather()
     fun newWeatherLocation()


}


/**
 * Make a Object @weatherBox that consists of the Weather datatype and the defined picture,
 * is ready to be displayed by sending it to the
 *
 *
 * Make a list of
 */

class WeatherVM (
    private val weatherRepository: WeatherRepository
):WeatherViewModel,ViewModel() {
    private var job: Job? = null
    private var timerJob: Job? = null

    private val _secondsElapsed = MutableLiveData<Int>(0)
    override val secondsElapsed: LiveData<Int>
        get() = _secondsElapsed



    //val weatherData: StateFlow<Weather?> = _weatherData.asStateFlow()
    private var _weeklyForecast = MutableStateFlow<WeeklyWeatherForecast>(WeeklyWeatherForecast())
    override val weeklyForecast: StateFlow<WeeklyWeatherForecast>
        get() = _weeklyForecast

    private var _coordinates = MutableStateFlow<Coordinates>(Coordinates(14.333f, 60.383f))
    override val coordinates: StateFlow<Coordinates>
        get() = _coordinates

    private var _internetConnection =MutableLiveData(true)
    override val internetConnection: LiveData<Boolean>
        get() = _internetConnection


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
                        _weeklyForecast.value=model.make_weather_Box(_coordinates.value.toString())


                    }catch (exception: Exception) {
                        Log.e("API Error", "Failed to fetch weather data", exception)
                    }
                }

                Log.d("Timer","Setting timer to 0  ")
                onCleared()
                startTimer(0)

            }else{}
        }

       // job?.cancel()


    }

    override fun newWeatherLocation() {

        job =viewModelScope.launch {
            try {
                //model.make_weather_Box(_coordinates.value.toString())
                _weeklyForecast.value=model.make_weather_Box(_coordinates.value.toString())


            }catch (exception: Exception) {
                Log.e("API Error", "Failed to fetch weather data", exception)
            }
        }
    }


    /*
        companion object {
            val Factory: ViewModelProvider.Factory = viewModelFactory {
                initializer {
                    val application = (this[APPLICATION_KEY] as WeatherApplication)
                    WeatherVM(application.weatherRepository)
                }
            }
        }

     */

    companion object {
        val Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(WeatherVM::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return WeatherVM(WeatherRepository()) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
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

        startTimer(300)
        /*
        // Code that runs during creation of the vm
        viewModelScope.launch {
            try {
              //  val weather = weatherRepository.
               // _weatherData.value = weather

            } catch (e: Exception) {
                // Handle exceptions, possibly updating a UI state to show an error message
            }
        }

         */

    }

}