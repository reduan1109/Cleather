package com.example.cleather.dataSource

import android.util.Log
import com.prof.rssparser.Channel
import com.prof.rssparser.Parser

/*
* This is where calls to the MetAlerts API is being done. It returns a Channel object*/
class MetAlertsApi  {
    companion object {
        //Base URL for API
        private const val baseUrl = "https://in2000-apiproxy.ifi.uio.no/weatherapi/metalerts/1.1/"

        suspend fun fetchMetAlert(latitude: Double, longitude: Double, english: Boolean = false): Channel? {
            //Build API query
            val query = baseUrl + "?" +
                    (if (english) "lang=en" else "") +
                    "&lat=" + latitude.toString() +
                    "&lon=" + longitude.toString()

            //Tries to parse RSS feed. Fun fact; not XML but RSS
            return try {
                Parser.Builder().build().getChannel(query)
            } catch(e: Exception) {
                Log.e("fetchMetAlert() DATASOURCE", e.toString())
                return null
            }
        }
    }
}