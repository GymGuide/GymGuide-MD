package com.example.gymguide.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment
import android.content.res.Resources
import android.location.Location

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.gymguide.R
import com.example.gymguide.databinding.FragmentMapsBinding
import com.example.gymguide.databinding.FragmentScanBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task

class MapsFragment : Fragment(), GoogleMap.OnInfoWindowClickListener {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!

    private val requestLocationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Log.e("MapsFragment", "Location permission allowed")
            } else {
                Log.e("MapsFragment", "Location permission denied")
                Toast.makeText(
                    requireContext(),
                    "Please accept the permission before accessing this feature!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private val callback = OnMapReadyCallback { googleMap ->

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("MapFragment", "Permission is already granted.")
            updateLocation { userLocation ->
                addRandomMarkersNearby(googleMap, userLocation) // FFBA69

                googleMap.addMarker(MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.user_node))
                    .position(userLocation)
                )
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation))
                googleMap.setOnInfoWindowClickListener(this)
            }
        } else {
            requestLocationPermission.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }

        googleMap.setOnMapClickListener {
            displayCustomInfoWindow(false)
        }

        // TODO: Implement pass data
        googleMap.setOnMarkerClickListener {
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(it.position), 300, null)
            displayCustomInfoWindow(true)
        }

        googleMap.isIndoorEnabled = false
        googleMap.isBuildingsEnabled = false
        googleMap.uiSettings.isCompassEnabled = false
        setMapStyle(googleMap)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onInfoWindowClick(marker: Marker) {
        val intent = Intent(requireContext(), DetailTrainerActivity::class.java)
        intent.putExtra("name", "Brendon Guidelines")
        intent.putExtra("nim", "SCP-079")
        startActivity(intent)
    }

    private fun setMapStyle(googleMap: GoogleMap) {
        context?.let {
            try {
                val success =
                    googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(it, R.raw.map_style))
                if (!success) {
                    Log.e("MapsFragment", "Map styling failed")
                } else {
                    Log.d("MapsFragment", "Map styling succeed")
                }
            } catch (exception: Resources.NotFoundException) {
                Log.e("MapsFragment", "Map styling failed")
            }
        }
    }

    private fun updateLocation(callback: (LatLng) -> Unit) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                if (it != null) {
                    Log.d("MapFragment", "Success getting location ${it.latitude} ${it.longitude}")
                    val userLocation = LatLng(it.latitude, it.longitude)
                    callback(userLocation)
                } else {
                    Log.e("MapsFragment", "Last known location is null")
                }
            }
        } else {
            requestLocationPermission.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun addRandomMarkersNearby(googleMap: GoogleMap, centerLocation: LatLng) {
        val numberOfMarkers = 10
        val distanceRange = 0.01

        val drawableArray = arrayOf(
            R.drawable.trainer_node1,
            R.drawable.trainer_node2,
            R.drawable.trainer_node3
        )

        for (i in 0 until numberOfMarkers) {
            val randomLatOffset = (Math.random() - 0.5) * 2 * distanceRange
            val randomLngOffset = (Math.random() - 0.5) * 2 * distanceRange

            val randomLat = centerLocation.latitude + randomLatOffset
            val randomLng = centerLocation.longitude + randomLngOffset
            val randomLocation = LatLng(randomLat, randomLng)

            val randomIndex = (drawableArray.indices).random()


            googleMap.addMarker(MarkerOptions()
                .position(randomLocation)
                .icon(BitmapDescriptorFactory.fromResource(drawableArray[randomIndex])))
        }
    }

    private fun displayCustomInfoWindow(visibility: Boolean): Boolean {
        if (visibility) {
            binding.layout.visibility = View.VISIBLE
            binding.layout.animate()
                .alpha(1f)
                .setDuration(300)
                .setListener(null)
        } else {
            binding.layout.animate()
                .alpha(0f)
                .setDuration(150)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        binding.layout.visibility = View.GONE
                    }
                })
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}