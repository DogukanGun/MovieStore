package com.dag.moviestore.dailogbox.authentication

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.dag.moviestore.dailogbox.DefaultModelDialog
import com.dag.moviestore.ui.onboard.MainActivity
import com.dag.moviestore.dailogbox.ModelDialog
import com.dag.moviestore.dailogbox.ModelDialogButton
import com.dag.moviestore.dailogbox.ModelDialogHandler
import com.dag.moviestore.R
import com.dag.moviestore.base.general.MovieStoreSessionUtil
import com.dag.moviestore.base.ui.MovieStoreViewState
import com.dag.moviestore.ext.openActivity
import javax.inject.Inject

object SessionTimeoutViewEffect: MovieStoreViewState

class SessionTimeoutHandler @Inject constructor(
    private val modelDialogHandler: ModelDialogHandler,
){

    fun handle(activity: FragmentActivity){
        MovieStoreSessionUtil.isSessionEndingProcessStarted = true
        modelDialogHandler.showDialog(activity,createUnAuthenticateDialog(activity))
    }

    private fun createUnAuthenticateDialog(activity: FragmentActivity): ModelDialog {
        return DefaultModelDialog(
            titleRes = R.string.app_name,
            messageRes = R.string.app_name,
            positiveButton = ModelDialogButton(
                textRes = R.string.app_name,
                onClick = {
                    MovieStoreSessionUtil.endSession()
                    activity.openActivity(
                        Intent(activity, MainActivity::class.java)
                    )
                }
            ),
            isCancelable = false
        )
    }

}