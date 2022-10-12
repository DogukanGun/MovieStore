package com.dag.moviestore.base.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.dag.moviestore.ext.tryCatch
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY

class LocationHelperGms(context: Context) : LocationCallback(), ILocationHelper {

    private val fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private val settingsClient: SettingsClient = LocationServices.getSettingsClient(context)
    private val locationRequest = LocationRequest()
    private var helperCallback: LocationHelperCallback? = null

    init {
        if (context is LocationHelperCallback) {
            this.helperCallback = context
        }
        locationRequest.interval = UPDATE_INTERVAL
        locationRequest.fastestInterval = FASTEST_INTERVAL
        locationRequest.priority = PRIORITY_HIGH_ACCURACY
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, this, Looper.myLooper())
        helperCallback?.onLocationRequest()
    }

    override fun requestLocationUpdatesBySettingsCheck() {
        val locationSettingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .build()
        settingsClient.checkLocationSettings(locationSettingsRequest)
            .addOnSuccessListener {
                requestLocationUpdates()
            }
            .addOnFailureListener {

                if (it is ApiException) {
                    if (it.statusCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                        helperCallback?.onResolutionRequired(it)
                    }
                }
            }
    }

    private fun removeLocationUpdates() {
        tryCatch(tryBlock = {
            fusedLocationProviderClient.removeLocationUpdates(this)
                .addOnSuccessListener {}
                .addOnFailureListener {}
        })
    }

    override fun onLocationAvailability(p0: LocationAvailability) {
        if (!p0.isLocationAvailable) {
            helperCallback?.onLocationFailed()
        }

    }

    override fun onLocationResult(p0: LocationResult) {
        p0.lastLocation?.let {
            helperCallback?.onLocationResult(it)
            removeLocationUpdates()
        }
    }

    interface LocationHelperCallback {
        fun onLocationResult(location: Location)
        fun onLocationFailed()
        fun onLocationRequest()
        fun onResolutionRequired(ex: Exception)
    }

    companion object {
        private const val UPDATE_INTERVAL = (6000).toLong()
        private const val FASTEST_INTERVAL: Long = 2000
    }
}
