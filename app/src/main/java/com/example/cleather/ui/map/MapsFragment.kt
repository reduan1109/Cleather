package com.example.cleather.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.cleather.R
import com.example.cleather.databinding.FragmentMapsBinding
import com.example.cleather.ui.sharedViewModel.CreateTripViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private lateinit var viewModel: MapsFragmentViewModel
    private lateinit var sharedViewModel : CreateTripViewModel

    private var currentMarker: Marker? = null

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding

    private var latitude: Double? = null
    private var longitude: Double? = null

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */


        //GET CURRENT LATITUDE & LONGITUDE, and set them here (currently hardcoded)
        val oslo = LatLng(59.919206, 10.797100)
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(oslo))

        val zoomLevel = 10.0f //This goes up to 21
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(oslo, zoomLevel))

        googleMap.setOnMapClickListener { point ->
            googleMap.clear()
            currentMarker = googleMap.addMarker(MarkerOptions().position(point))

            latitude = currentMarker?.position?.latitude
            longitude = currentMarker?.position?.longitude

            binding?.fab?.isEnabled = true
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMapsBinding.inflate(inflater, container, false)

        _binding?.apply {
            viewModel = MapsFragmentViewModel()
            sharedViewModel = CreateTripViewModel()
        }
        //return inflater.inflate(R.layout.fragment_maps, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        if(latitude == null || longitude == null){
            binding?.fab?.isEnabled = false
        }

        binding?.fab?.setOnClickListener {
            //Upload coordinates to room
            //viewModel.UploadCoordinates(latitude!!, longitude!!)
            //sharedViewModel.setCoordinates(latitude!!, longitude!!)
            val model = ViewModelProvider(requireActivity())[CreateTripViewModel::class.java]

            model.setCoordinates(latitude!!, longitude!!)

            //Change fragment
            findNavController().navigate(R.id.action_MapFragment_to_ConfirmFragment)
        }
    }
}