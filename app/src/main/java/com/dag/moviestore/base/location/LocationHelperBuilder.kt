package com.dag.moviestore.base.location

import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability

class LocationHelperBuilder constructor(
    var context: Context
) {
    fun getLocationHelper(): ILocationHelper {
        val code = GoogleApiAvailability.getInstance()
            .isGooglePlayServicesAvailable(context.applicationContext)
        return if (code != ConnectionResult.SUCCESS) {
            LocationHelperHms(context)
        } else {
            LocationHelperGms(context)
        }
    }
}