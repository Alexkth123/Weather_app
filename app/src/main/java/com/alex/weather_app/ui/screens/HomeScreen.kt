package com.alex.weather_app.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alex.weather_app.data.Weather_Box
import com.alex.weather_app.data.WeeklyWeatherForecast
import com.alex.weather_app.ui.viewmodels.WeatherViewModel


@Composable
fun HomeScreen(
    vm: WeatherViewModel
) {
    val weeklyForecast by vm.weeklyForecast.collectAsState()


    
    Column (modifier = Modifier
        .fillMaxSize()
        .background(Color.Blue)
        .padding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

        Row (
            modifier = Modifier
                .background(Color.Red)
                .padding(),
        ){
            Text(text = "Location", fontSize = 16.sp)

        }

        Column (modifier = Modifier
            .fillMaxSize()
            .background(Color.White, RoundedCornerShape(10.dp))
            .padding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = " Button API", fontSize = 46.sp)

            Button(onClick = { vm.getWeather() }) {}


            Column (modifier = Modifier){


                LazyColumn(modifier = Modifier.padding(10.dp).background(Color.Transparent,RoundedCornerShape(10.dp))) {
                    item {
                        Text(text = "Weekly Forecast")
                    }
                    // This for-loop will create list items for each day's forecast
                    for (i in 0 until 7) {
                        item {
                            WeatherDayItem(weeklyForecast.getWeatherBoxForDay(i))
                        }


                    }

                }


                }
            }

        }



        
    }


@Composable
fun WeatherDayItem(weatherBox: Weather_Box?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
       // elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Weather on day: ", fontWeight = FontWeight.Bold)
            if (weatherBox != null) {
                Text(text = "Temp: ${weatherBox.weather.temperature.toString()}°")
            }
            if (weatherBox != null) {
                Text(text = "Rain: ${weatherBox.weather.rain.toString()}%")
            }
            if (weatherBox != null) {
                Text(text = "Wind Speed: ${weatherBox.weather.wind_speed.toString()}m/s")
            }
            // Add additional weather info here
        }
    }
}


/*
@Composable
fun scroll(weeklyForecast: WeeklyWeatherForecast) {
    LazyColumn(modifier = Modifier.padding(10.dp)) {
        item {
            Text(text = "Weekly Forecast", style = MaterialTheme.typography.h6)
        }
        // This for-loop will create list items for each day's forecast
        for (i in 0 until 7) {
            WeatherDayItem(weeklyForecast.getWeatherBoxForDay(i))

        }

    }
}

 */