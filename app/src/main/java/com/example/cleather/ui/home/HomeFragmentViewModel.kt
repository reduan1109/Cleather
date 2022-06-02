package com.example.cleather.ui.home

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cleather.MainActivityViewModel
import com.example.cleather.dataClasses.TripInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragmentViewModel: ViewModel() {
    private val tripInfoList = MutableLiveData<MutableList<TripInfo>>()

    //A simple internet availability check
    fun internetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        if (connectivityManager != null) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
        }
        return false
    }

    //Fetches/prepares the list of trips that is supposed to be viewed
    fun fetchTripInfo(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            MainActivityViewModel.getTrips().also{
                tripInfoList.postValue(it)
            }
        }
    }

    //Actually gives the liveData
    fun getTripInfo(): LiveData<MutableList<TripInfo>> = tripInfoList
}
