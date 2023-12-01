package com.alex.weather_app.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
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
import com.alex.weather_app.data.WeeklyWeatherForecast
import com.alex.weather_app.ui.viewmodels.WeatherViewModel
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.platform.LocalContext
import com.alex.weather_app.data.weather_type
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun HomeScreen(
    vm: WeatherViewModel
) {
    val weeklyForecast by vm.weeklyForecast.collectAsState()
    val context = LocalContext.current
    //vm.getWeather()



    //val isInternetConnected = isInternetAvailable(context)
    val isInternetConnected : MutableStateFlow<Boolean> = MutableStateFlow(isInternetAvailable(context))




    
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



            if(true){
                Text(text = "Internet")
            }else{
                Text(text = "No Internet")
            }

            MyInputComponent(vm)

        }

        Column (modifier = Modifier
            .fillMaxSize()
            .background(Color.White, RoundedCornerShape(10.dp))
            .padding(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = " Button API"+ weather_type.`☀️`, fontSize = 46.sp)

            Button(onClick = { vm.getWeather() }) {}


            Column (modifier = Modifier.background(Color.White, RoundedCornerShape(10.dp))){


                LazyColumn(modifier = Modifier
                    .padding(10.dp)
                    .background(Color.Transparent, RoundedCornerShape(10.dp))) {
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
                Text(text = "${weatherBox.weatherType}")
                Text(text = "Temp: ${weatherBox.weather.temperature.toString()}°")
                Text(text = "Rain: ${weatherBox.weather.rain.toString()}%")
                Text(text = "Wind Speed: ${weatherBox.weather.wind_speed.toString()}m/s")


            }
            // Add additional weather info here
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyInputComponent(vm: WeatherViewModel) {
    var text by remember { mutableStateOf("") }
    var firstInput by remember { mutableStateOf("") }
    var secondInput by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .padding(6.dp)
        .background(Color.Green, RoundedCornerShape(10.dp))) {

       /*
        TextField(
            value = text,
            onValueChange = { newText -> text = newText },
            label = { Text("Enter text") },

        )

        */
        TextField(
            value = firstInput,
            onValueChange = { firstInput = it },
            label = { Text("LAT") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier

        )

        TextField(
            value = secondInput,
            onValueChange = { secondInput = it },
            label = { Text("LON") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.background(Color.Magenta, RoundedCornerShape(10.dp))
        )



        // You can use the 'text' variable here to display the input or perform actions
        Button(onClick = {
            // Do something with the text

            val firstFloat = firstInput.toFloatOrNull()
            val secondFloat = secondInput.toFloatOrNull()

            if (firstFloat != null && secondFloat != null) {
                // Do something with the float values
                //Check if the cordinates are new
               // if(vm.coordinates.value.lat.toString()!=firstInput&& vm.coordinates.value.lon.toString()!=secondInput){}
                // Can be useful to check if the cordinates are new

                vm.coordinates.value.setCoordinates(firstFloat,secondFloat)
                vm.newWeatherLocation()
            } else {
                // Handle invalid input
            }

        }) {
            Text("Search")
        }
    }
}


@Composable
fun MyConnectivityAwareComponent() {
    val context = LocalContext.current
    val isInternetConnected = isInternetAvailable(context)

    if (isInternetConnected) {
        // UI for when the internet is available
    } else {
        // UI for when the internet is not available
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