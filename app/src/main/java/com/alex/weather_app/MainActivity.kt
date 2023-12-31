package com.alex.weather_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alex.weather_app.ui.screens.HomeScreen
import com.alex.weather_app.ui.theme.Weather_appTheme
import com.alex.weather_app.ui.viewmodels.WeatherVM


public class MainActivity : ComponentActivity() {

    val weatherViewModel: WeatherVM by viewModels {
        WeatherVM.createFactory(applicationContext)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Weather_appTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    // Instantiate the homescreen
                    HomeScreen(vm = weatherViewModel)

                    //Greeting("Android and axe")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Todays weather $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Weather_appTheme {
        Greeting("Android")
    }
}