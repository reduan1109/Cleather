package com.example.cleather.dataClasses

import com.example.cleather.dataSource.locationForecastClasses.DayForecast


data class DayObject(val forecast: DayForecast?, var clothes: MutableList<TripItem>)