package com.example.cleather.ui.sharedViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//This is a shared View Model between map & confirm fragments

class CreateTripViewModel: ViewModel() {
    private val longitudeLatitude = MutableLiveData<Pair<Double, Double>>()

    fun setCoordinates(lat: Double, lon: Double){
        longitudeLatitude.value = Pair(lat, lon)
    }

    fun getCoordinates(): MutableLiveData<Pair<Double, Double>>{
        return longitudeLatitude
    }
}