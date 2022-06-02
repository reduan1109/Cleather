package com.example.cleather.dataSource.locationForecastClasses

import com.google.gson.annotations.SerializedName
import java.time.LocalDate


data class LocationForecast (

  @SerializedName("type"       ) var type       : String?     = null,
  @SerializedName("geometry"   ) var geometry   : Geometry?   = Geometry(),
  @SerializedName("properties" ) var properties : Properties? = Properties()

) {

  //Returns a list of forecast in a date range. Simply groups the time series based on date and returns a list
  fun getGetListOfDayForecasts(startDate: LocalDate, endDate: LocalDate): List<DayForecast>? {
    val outPut = mutableListOf<DayForecast>()

    this.properties?.timeseries ?: return null

    for (timePoint in properties?.timeseries!!) {
      val timePointDate = LocalDate.parse(timePoint.time?.dropLast(10)) //Time of day not important for grouping
      val tmpForecast = DayForecast(timePointDate)
      tmpForecast.timeSeries = mutableListOf(timePoint)


      var flag = false //Concurrency fix


      for(day in outPut) {
        if (day.date.isEqual(timePointDate)){
          flag = true
          break
        }
      }


      if (!flag) {
        outPut.add(tmpForecast)
      } else {
        outPut.first{it.date == timePointDate}.timeSeries?.add(timePoint)
      }

    }


    return if (outPut.isEmpty()) null
      else outPut.filter {
          (it.date.isAfter(startDate) || it.date.isEqual(startDate)) &&
          (it.date.isBefore(endDate) || it.date.isEqual(endDate))
      }
  }
}