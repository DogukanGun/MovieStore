package com.dag.moviestore.ext

import android.app.Activity
import android.app.Application
import android.os.Bundle


fun Application.lifeCycle(
    activityCreated: (Activity?) -> Unit,
    activityPaused: (Activity?) -> Unit,
    activityResumed: (Activity?) -> Unit
): Application.ActivityLifecycleCallbacks {

    return object : Application.ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity) {
            activityPaused(activity)
        }

        override fun onActivityResumed(activity: Activity) {
            activityResumed(activity)
        }

        override fun onActivityStarted(activity: Activity) {
        }

        override fun onActivityDestroyed(activity: Activity) {
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityStopped(activity: Activity) {
        }

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            activityCreated(activity)
        }
    }
}
