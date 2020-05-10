package ru.gmasalskih.weather3.data.storege.gps

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location as Coordinates
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import android.os.Looper
import android.provider.Settings
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import ru.gmasalskih.weather3.utils.toCoordinate
import ru.gmasalskih.weather3.utils.toast

object CoordinatesProvider {

    const val PERMISSION_ID = 42

    @SuppressLint("MissingPermission")
    fun getLastLocation(fragment: Fragment, callback: (lat: String, lon: String) -> Unit) {
        if (checkPermissions(fragment)) {
            if (isLocationEnabled(fragment)) {
                val fusedLocationProviderClient =
                    getFusedLocationProviderClient(fragment.requireActivity())
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(fragment.requireActivity()) { task ->
                    val coordinates: Coordinates? = task.result
                    if (coordinates == null) {
                        requestNewLocationData(fusedLocationProviderClient, callback)
                    } else {
                        callback(
                            coordinates.latitude.toString().toCoordinate(),
                            coordinates.longitude.toString().toCoordinate()
                        )
                    }
                }
            } else {
                "Turn on location".toast(fragment.requireContext())
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                fragment.startActivity(intent)
            }
        } else {
            requestPermissions(fragment)
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData(
        fusedLocationProviderClient: FusedLocationProviderClient,
        callback: (lat: String, lon: String) -> Unit
    ) {
        val locationRequest = LocationRequest().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 0
            fastestInterval = 0
            numUpdates = 1
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    callback(
                        locationResult.lastLocation.latitude.toString().toCoordinate(),
                        locationResult.lastLocation.longitude.toString().toCoordinate()
                    )
                }
            },
            Looper.myLooper()
        )
    }

    private fun isLocationEnabled(fragment: Fragment): Boolean {
        val locationManager =
            fragment.requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun checkPermissions(fragment: Fragment) = (ActivityCompat.checkSelfPermission(
        fragment.requireContext(),
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        fragment.requireContext(),
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED)

    private fun requestPermissions(fragment: Fragment) {
        fragment.requestPermissions(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }
}