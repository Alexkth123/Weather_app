package com.alex.weather_app.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.alex.weather_app.WeatherApplication
import com.alex.weather_app.data.Weather
import com.alex.weather_app.data.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


interface WeatherViewModel{

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

    private val _weatherData = MutableStateFlow<Weather?>(null)
    val weatherData: StateFlow<Weather?> = _weatherData.asStateFlow()








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





    init {
        // Code that runs during creation of the vm
        viewModelScope.launch {
            try {
                val weather = weatherRepository.getWeatherData("cali")
                _weatherData.value = weather
            } catch (e: Exception) {
                // Handle exceptions, possibly updating a UI state to show an error message
            }
        }
    }



}