package ru.gmasalskih.weather3.data.storege.gps

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener
import ru.gmasalskih.weather3.data.entity.Coordinates
import ru.gmasalskih.weather3.utils.toCoordinate

class CoordinatesProvider(private val context: Context) {

    private fun addCoordinate(lat: Double, lon: Double) =
        Coordinates(
            lat = lat.toString().toCoordinate(),
            lon = lon.toString().toCoordinate()
        )

    fun retrieveLastKnownCoordinate(
        onSuccess: (coordinate: Coordinates) -> Unit,
        onDeny: () -> Unit,
        onEmpty: () -> Unit
    ) {
        Dexter.withContext(context)
            .withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            .withListener(object : BaseMultiplePermissionsListener() {
                @SuppressLint("MissingPermission")
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report != null && report.areAllPermissionsGranted()) {
                        LocationServices.getFusedLocationProviderClient(context).lastLocation.addOnSuccessListener { location ->
                            if (location == null) {
                                onEmpty()
                            } else {
                                onSuccess(
                                    addCoordinate(
                                        lat = location.latitude,
                                        lon = location.longitude
                                    )
                                )
                            }
                        }
                    } else {
                        onDeny()
                    }
                }
            }).check()
    }
}