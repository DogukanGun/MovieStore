package com.dag.moviestore.base.general

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.dag.moviestore.ext.lifeCycle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import dagger.hilt.android.HiltAndroidApp
import java.util.*
import javax.inject.Inject

@HiltAndroidApp
class MovieStoreApplication : MultiDexApplication(), Observer {

    @Inject
    lateinit var movieStoreActivityListener: MovieStoreActivityListener
    private var currentActivity: CanDropSession? = null

    private var sessionShouldEnd = false

    companion object {
        var market: MarketType = MarketType.HUAWEI
        lateinit var appInstance: MovieStoreApplication
        var baseUrl = "http://www.omdbapi.com/"
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate() {
        super.onCreate()
        val code = GoogleApiAvailability.getInstance()
            .isGooglePlayServicesAvailable(applicationContext)
        if (code == ConnectionResult.SUCCESS) {
            market = MarketType.GOOGLE
        }
        appInstance = this
        ApplicationSessionManager.addObserver(this)
        val movieStoreAppLifeCycle = this.lifeCycle(
            activityCreated = {
                // All Activities Portrait
                it?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

            },
            activityResumed = {
                if (it is CanDropSession) {
                    currentActivity = it
                    if (sessionShouldEnd) {
                        currentActivity?.dropSession()
                        sessionShouldEnd = false
                    }
                }
            },
            activityPaused = {
                if (it == currentActivity) {
                    currentActivity = null
                }
            }
        )
        registerActivityLifecycleCallbacks(movieStoreActivityListener)
        registerActivityLifecycleCallbacks(movieStoreAppLifeCycle)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        ApplicationSessionManager.deleteObserver(this)
    }

    override fun update(p0: Observable?, p1: Any?) {
        sessionShouldEnd = true
        currentActivity?.let {
            it.dropSession()
            sessionShouldEnd = false
        }
    }
}