package com.example.cleather.dataSource

import android.util.Log
import com.example.cleather.dataSource.locationNameFinderClasses.LocationName
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.coroutines.awaitString
import com.google.gson.Gson

/*
* This is where api calls to the Reverse geocode client API is being called.*/
class LocationNameFinderAPi {
    companion object {
        //Base URL for API
        private const val baseUrl = "https://api.bigdatacloud.net/data/reverse-geocode-client?"

        suspend fun fetchLocationName(latitude: Double, longitude: Double, english: Boolean = false): LocationName? {
            //Builds API query
            val query = baseUrl +
                    "latitude=" + latitude.toString() +
                    "&longitude=" + longitude.toString() +
                    if (english) "&localityLanguage=en" else "&localityLanguage=no"

            //Tries to parse json from query
            return try {
                Gson().fromJson(Fuel.get(query).awaitString(), LocationName::class.java)
            } catch(e: Exception) {
                Log.e("fetchLocationName() DATASOURCE", e.toString())
                return null
            }
        }
    }
}
