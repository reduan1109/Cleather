package com.example.cleather.ui.confirm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.cleather.MainActivityViewModel
import com.example.cleather.R
import com.example.cleather.databinding.FragmentConfirmBinding
import com.example.cleather.ui.sharedViewModel.CreateTripViewModel
import com.google.android.material.datepicker.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ConfirmFragment : Fragment() {

    private var _binding: FragmentConfirmBinding? = null
    private lateinit var viewModel: ConfirmFragmentViewModel

    private var startDate: Long = 0
    private var endDate: Long = 0

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConfirmBinding.inflate(inflater, container, false)

        _binding?.apply {
            viewModel = ConfirmFragmentViewModel()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Get trip info
        val sharedViewModel = ViewModelProvider(requireActivity())[CreateTripViewModel::class.java]

        val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker().setTitleText("velg dato").apply {
            //Constrains dates / removes all dates before today
            val days = 8
            val constraintsBuilder = CalendarConstraints.Builder().setValidator(
                CompositeDateValidator.allOf(
                    listOf (
                        DateValidatorPointForward.now(),
                        DateValidatorPointBackward.before((days*24*60*60*1000 + System.currentTimeMillis())))
                )
            )
            setCalendarConstraints(constraintsBuilder.build())
        }.build()

        binding.CalendarButton.setOnClickListener {
            dateRangePicker.show(parentFragmentManager, "date_range_picker")
        }

        dateRangePicker.addOnPositiveButtonClickListener { datePicked ->
            startDate = datePicked.first
            endDate = datePicked.second

            showDate(viewModel.dateToLocalDateTime(datePicked.first).toLocalDate(), viewModel.dateToLocalDateTime(datePicked.second).toLocalDate())

            //WAIT UNTIL COORDINATES ARE SET
            while(viewModel.getTripCoordinates() == null){Thread.sleep(50)}

            if(viewModel.getTripCoordinates() != null){
                val dates: Pair<LocalDateTime, LocalDateTime> = Pair(viewModel.dateToLocalDateTime(datePicked.first), viewModel.dateToLocalDateTime(datePicked.second))
                viewModel.setTripDates(dates)
                viewModel.fetchWeatherData(viewModel.dateToLocalDateTime(datePicked.first).toLocalDate(), viewModel.dateToLocalDateTime(datePicked.second).toLocalDate())
            }
        }

        //Get coordinates from sharedViewModel
        sharedViewModel.getCoordinates().observe(viewLifecycleOwner) {
            viewModel.setTripCoordinates(it)

            //get location name from coordinates
            viewModel.fetchAlertData(it.second, it.first)
            viewModel.fetchLocationName(it.first, it.second)
        }

        viewModel.getLocationName().observe(viewLifecycleOwner) {
            binding.LocationText.text = it?.principalSubdivision + ", " + it?.locality
        }

        //Create trip button
        binding.ConfirmButton.setOnClickListener {
            //Checking if the trips name is not blank
            if(_binding != null) {
                if(!(_binding?.TripName?.text.isNullOrBlank())) {
                    val tripName = _binding?.TripName?.text.toString()

                    val trip = viewModel.generateTrip(tripName)

                    if(trip != null) {
                        context?.let { it -> MainActivityViewModel.addTrip(it, trip) }


                        findNavController().navigate(R.id.action_ConfirmFragment_to_HomeFragment)
                    }
                    else Toast.makeText(context, "Failed to generate trip", Toast.LENGTH_LONG).show()

                }
                else Toast.makeText(context, "Invalid input", Toast.LENGTH_LONG).show()

            }
        }
    }

    private fun showDate(start: LocalDate, end: LocalDate){
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM")

        if(startDate != endDate)
            binding.DateText.text = resources.getString(R.string.date_range_format, start.format(formatter), end.format(formatter))
        else
            binding.DateText.text = resources.getString(R.string.date_format, start.format(formatter))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}