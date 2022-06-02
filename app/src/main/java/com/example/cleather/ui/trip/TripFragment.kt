package com.example.cleather.ui.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cleather.R
import com.example.cleather.adapterClasses.AlertAdapter
import com.example.cleather.adapterClasses.DayWeatherAdapter
import com.example.cleather.adapterClasses.ItemIconAdapter
import com.example.cleather.databinding.FragmentTripBinding
import java.time.format.DateTimeFormatter

/*
* This is the screen the user sees after pressing on trip summary card (the boxes with the map as background).
* The fragments job is to display the trips  information from the database in a sensible way.
* All of the actual logic is happening in the TripFragmentViewModel as per the MVVM architecture.
* */

class TripFragment : Fragment() {
    private var _binding: FragmentTripBinding? = null
    private val viewModel: TripFragmentViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTripBinding.inflate(inflater, container, false)

        //Prepares the trip to display
        viewModel.fetchTripToDisplay()

        //Setts the title to the name of the trip
        binding.tripName.text = viewModel.getTripToDisplay().tripName

        //TODO: Edit toolbar title so it reads something that useful [Medium]

        //Setts the date of the trip. The formatter is a LocalDateTime procedure that returns pretty print.
        // String resources is used
        binding.tripDate.text = resources.getString(
            R.string.date_range_format,
            viewModel.getTripToDisplay().tripStartDate.format(DateTimeFormatter.ofPattern("dd.MM")),
            viewModel.getTripToDisplay().tripEndDate.format(DateTimeFormatter.ofPattern("dd.MM"))
        )

        //Setts the adapter for the recycler that shows the forecast for that day.
        binding.weatherRecycler.adapter = DayWeatherAdapter(viewModel.getTripToDisplay().dayObjects)

        //Setts the clothes adapter, with the clothes of the trip.
        binding.itemRecycler.adapter = ItemIconAdapter(viewModel.getTripToDisplay().allClothes.toList())
        //Setts a layout manager for the mentioned adapter above.
        binding.itemRecycler.layoutManager = GridLayoutManager(context, 2, LinearLayoutManager.HORIZONTAL, false)

        //If no dangers to alert, remove title
        if (viewModel.getTripToDisplay().tripRssAlert?.articles.isNullOrEmpty())
            binding.dangerTitle.text = ""
        else
            binding.dangerRecycler.adapter = viewModel.getTripToDisplay().tripRssAlert?.articles?.let {
                AlertAdapter(
                    it
                )
            }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Make FAB clickable
        binding.fab.setOnClickListener {
            Toast.makeText(context, R.string.unsupported, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}