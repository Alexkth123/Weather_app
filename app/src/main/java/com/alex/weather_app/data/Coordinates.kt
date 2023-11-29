package com.alex.weather_app.data

public class Coordinates (

    var lon : Float,
    var lat : Float
){
    fun setCoordinates( lon : Float, lat : Float){
        this.lon=lon
        this.lat=lat
    }

    override fun toString():String{
        var str= "lon/"+lon.toString()+"/lat/"+lat.toString()
        return str
    }
}
