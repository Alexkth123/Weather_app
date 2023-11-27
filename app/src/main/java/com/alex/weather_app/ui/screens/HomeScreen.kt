package com.alex.weather_app.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alex.weather_app.ui.viewmodels.WeatherViewModel

@Composable
fun HomeScreen(
    vm: WeatherViewModel
) {


    
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

            Button(onClick = { vm.getWeather() }) {

            }

        }



        
    }

}