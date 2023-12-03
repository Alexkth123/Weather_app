package com.alex.weather_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alex.weather_app.data.Weather_Box
import com.alex.weather_app.ui.viewmodels.WeatherViewModel
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.alex.weather_app.R
import com.alex.weather_app.data.WeatherDay
import com.alex.weather_app.data.WeeklyWeatherForecast
import com.alex.weather_app.data.weather_type
import com.alex.weather_app.ui.theme.StandbyBlue
import com.alex.weather_app.ui.theme.StyleBlue
import com.alex.weather_app.ui.viewmodels.WeatherVM
import kotlinx.coroutines.delay


@Composable
fun HomeScreen(
    vm: WeatherViewModel
) {
    val weeklyForecast by vm.weeklyForecast.collectAsState()
    val context = LocalContext.current

    var isInternetConnected by remember { mutableStateOf(true) }
    var showSearchMenu by remember { mutableStateOf(false) }

    val clickedWeatherBox by vm.clickedWeatherBox.collectAsState()

    // This LaunchedEffect will run when the component is first launched
    LaunchedEffect(Unit) {
        while (true) {
            // Check for internet connectivity
            isInternetConnected = isInternetAvailable(context)

            if(!isInternetConnected){
                showSearchMenu = false
            }

            // Check internet interval
            delay(1000)
        }
    }

    // Main Column for HomeScreen
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue)
            .padding(0.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        // Coordinates Input / Internet Error message Column
        Column (
            modifier = Modifier
                .background(if (isInternetConnected) StyleBlue else Color.Red)
                .padding(0.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            if(!isInternetConnected){
                Text(
                    text = "No Internet!",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    ),
                )
            }else{
                if(showSearchMenu) {
                    MyInputComponent(vm)
                }
            }
        }

        // Second Main Column - forecast/buttons
        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(0.dp),

            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    showSearchMenu = !showSearchMenu
                },
                enabled = (isInternetConnected),
                colors = ButtonDefaults.buttonColors(
                    contentColor = if(showSearchMenu && isInternetConnected) StyleBlue else Color.White),
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Icon(
                    painter = painterResource( if (showSearchMenu && isInternetConnected) R.drawable.up else R.drawable.down),
                    contentDescription = "menu",
                    modifier = Modifier
                        .height(70.dp)
                )
            }
            Text(text = "Weather " + weather_type.`☀️`, fontSize = 46.sp)

            Button(
                onClick = {
                    vm.getWeather()
                }
            ) {
                Text(text = "Test KTH API")
            }

            // Weekly Forecast Column - Scrollable
            Column (
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(10.dp)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(text = "Weekly Forecast")
                LazyColumn(
                    modifier = Modifier
                        .padding(10.dp)
                        .background(Color.Transparent, RoundedCornerShape(10.dp))
                ) {
                    item {
                        WeatherDayItem(vm, weeklyForecast, clickedWeatherBox)
                    }
                    // This for-loop will create list items for each day's forecast
                    /*for (day in WeatherDay.values()) {
                        item {

                            //WeatherDayItem(weeklyForecast.getDailyForecast(day).getHourlyForecast(12), int_to_day_string(day.int).name)
                        }
                    }*/
                }
            }
        }
    }
}
/*
@Composable
fun WeatherDayItem(weatherBox: Weather_Box?, dayOfWeek: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "${dayOfWeek}:", fontWeight = FontWeight.Bold)
            if (weatherBox != null) {
                Text(text = "${weatherBox.weatherType.emoji}")
                Text(text = "Temp: ${weatherBox.weatherParams.temperature.toString()}°")
                Text(text = "Humidity: ${weatherBox.weatherParams.relativeHumidity.toString()}%")
                Text(text = "Wind Speed: ${weatherBox.weatherParams.windSpeed.toString()}m/s")
            }
        }
    }
}*/


@Composable
fun WeatherDayItem(vm: WeatherViewModel, weeklyForecast: WeeklyWeatherForecast, clickedWeatherBox: Weather_Box?) {

    for (day in WeatherDay.values()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "${day}:", fontWeight = FontWeight.Bold)
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    val itemCount = 24
                    items(itemCount) { index ->
                        val value = 0 + index
                        val weatherBox = weeklyForecast.getDailyForecast(day).getHourlyForecast(value)
                        if (weatherBox != null) {
                            Box(
                                modifier = Modifier
                                    .width(70.dp)
                                    .height(100.dp)
                                    .background(
                                        color = if (weatherBox.equals(clickedWeatherBox)) StandbyBlue else Color.Transparent
                                    )
                                    .padding(8.dp)
                                    .clickable { vm.setClickedWeatherBox(weatherBox) },

                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(0.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = if (value < 10) "0${value}" else "${value}",
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                    Text(
                                        text = "${weatherBox.weatherType.emoji}",
                                        style = MaterialTheme.typography.headlineLarge
                                    )
                                    Text(
                                        text = "${weatherBox.weatherParams.temperature}°C",
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                }
                            }
                        }
                    }
                }
                if(clickedWeatherBox != null && clickedWeatherBox.weatherDate != "" && clickedWeatherBox.weatherDay == day) {
                    Text(text = "Humidity: ${clickedWeatherBox.weatherParams.relativeHumidity}%")
                    Text(text = "Wind Speed: ${clickedWeatherBox.weatherParams.windSpeed}m/s")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyInputComponent(vm: WeatherViewModel) {
    var text by remember { mutableStateOf("") }
    var firstInput by remember { mutableStateOf("") }
    var secondInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxWidth()
    ) {

        TextField(
            value = firstInput,
            onValueChange = { firstInput = it },
            label = { Text("LAT") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .background(Color.Magenta, RoundedCornerShape(50.dp))
                .fillMaxWidth()
        )

        TextField(
            value = secondInput,
            onValueChange = { secondInput = it },
            label = { Text("LON") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier
                .background(Color.Magenta, RoundedCornerShape(50.dp))
                .fillMaxWidth()
        )

        Button(
            onClick = {
                val firstFloat = firstInput.toFloatOrNull()
                val secondFloat = secondInput.toFloatOrNull()

                if (firstFloat != null && secondFloat != null) {

                    vm.coordinates.value.setCoordinates(firstFloat,secondFloat)
                    vm.newWeatherLocation()
                } else {
                    // Handle invalid input
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Submit",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

    return when {
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}