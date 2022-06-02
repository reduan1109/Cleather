package com.example.cleather.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cleather.R
import com.example.cleather.adapterClasses.TripSummaryAdapter
import com.example.cleather.databinding.FragmentHomeBinding

/*
* This is the first screen the user sees after a loading screen (if such a loading screen ever gets implemented).
* The fragments job is to display the trip objects from the database in a sensible way.
* All of the actual logic is happening in the HomeFragmentViewModel as per the MVVM architecture.
* */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val viewModel: HomeFragmentViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        //Fetches the data from source and gets the data ready in the viewModel
        context?.let { viewModel.fetchTripInfo(it) }

        //Actually does something with the data that is being fetched
        viewModel.getTripInfo().observe(viewLifecycleOwner) {
            //TODO: What happens if the list ("it") is empty? For the time being nothing [medium]
            if (it.size == 0)  {
                binding.welcome.setImageResource(R.drawable.welcome_drawing)
                binding.welcomeText.text = resources.getString(R.string.welcome_text)
            }
            else {
                binding.welcomeText.text = ""
                binding.welcome.setImageResource(0)
            }

            if (it.isNotEmpty())  binding.homeRecycler.adapter = TripSummaryAdapter(it) //Sets recycler adapter with tripInfo
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Make FAB clickable
        binding.fab.setOnClickListener {
            if (context?.let { it1 -> viewModel.internetAvailable(it1) } == true)
                findNavController().navigate(R.id.action_HomeFragment_to_MapFragment)
            else
                Toast.makeText(context, R.string.no_internett_error, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //Smart use of threads isn't that important onDestroy, however this gives bad juju
        _binding = null
    }
}