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
class Forcast_7day ( ) {
    // Array stateflow, since it should not be changeble
    //= MutableStateFlow(arrayOf<Weather_Box>())

    //private val forcast: MutableStateFlow<Array<Weather_Box>>=
    init {
        Log.d("GameVM", "The following sequence was generated:")


    }

    private fun sudo_forcast(){
        // Creates a fake forecast array
    }


}
