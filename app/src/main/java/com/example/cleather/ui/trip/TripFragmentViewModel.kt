package com.example.cleather.ui.trip

import androidx.lifecycle.ViewModel
import com.example.cleather.MainActivityViewModel
import com.example.cleather.dataClasses.TripInfo

class TripFragmentViewModel: ViewModel() {
    private lateinit var tripBeingDisplayed: TripInfo

    //Gets the trip that is supposed to be displayed. Is asserted as not null,
    // because the only reason this would be called would be if you pressed on a
    // trip in HomeFragment.
    fun fetchTripToDisplay() { tripBeingDisplayed = MainActivityViewModel.getDisplayedTrip()!! }

    //Actually return the trip being displayed.
    fun getTripToDisplay(): TripInfo = tripBeingDisplayed
}
