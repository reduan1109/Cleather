package com.example.cleather.dataSource

import android.util.Log
import com.example.cleather.dataSource.locationForecastClasses.DayForecast
import com.example.cleather.dataSource.locationForecastClasses.LocationForecast
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitString
import com.google.gson.Gson
import java.time.LocalDate

/*
* This is where api calls to the LocationForecast API is being called.*/
class LocationForecastApi {
    companion object {
        //Base URL for API
        private const val baseUrl = "https://in2000-apiproxy.ifi.uio.no/weatherapi/locationforecast/2.0/complete?"

        suspend fun fetchLocationForeCast(latitude: Double, longitude: Double, startDate: LocalDate, endDate: LocalDate): List<DayForecast>? {
            //Builds API query
            val query = baseUrl +
                    "lat=" + latitude.toString() +
                    "&lon=" + longitude.toString()

            //Tries to parse json from query
            return try {
                 Gson()
                     .fromJson(Fuel.get(query).awaitString(), LocationForecast::class.java)
                     .getGetListOfDayForecasts(startDate, endDate)  //TODO: Mild break on separation of concerns principle [low].
                                                                    // The whole object should be returned AND THEN something
                                                                    // something should get the days.
            } catch(e: Exception) {
                Log.e("fetchLocationForeCast() DATASOURCE", e.toString())
                return null
            }
        }
    }
}
