package com.example.cleather

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleather.dataClasses.TripInfo
import com.example.cleather.dataSource.LocalSaveApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel {
    companion object TripsContainer: ViewModel() {
        private var tripToBeDisplayed: TripInfo? = null
        var tripHolder = mutableListOf<TripInfo>()

        fun setDisplayedTrip(tripInfo: TripInfo) { tripToBeDisplayed = tripInfo }

        fun addTrip(context: Context, trip: TripInfo) {
            tripHolder.add(trip)

            viewModelScope.launch(Dispatchers.IO) {
                LocalSaveApi.saveTrips(tripHolder, context)
            }
        }

        fun getDisplayedTrip() = tripToBeDisplayed

        fun getTrips() = tripHolder

        fun readTripsFromCache(context: Context) {
            viewModelScope.launch(Dispatchers.IO) {
                tripHolder = LocalSaveApi.readTrips(context)?.toMutableList() ?: mutableListOf()
            }
        }
    }
}
