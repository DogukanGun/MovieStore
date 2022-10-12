package com.dag.moviestore.base.ui

import android.content.Context
import com.dag.moviestore.component.processdialog.AnimationType
import com.dag.moviestore.component.processdialog.MovieStoreProcessDialog
import javax.inject.Inject

class MovieStoreProgressDialogManager @Inject constructor() {

    companion object {
        private var progressDialog: MovieStoreProcessDialog? = null
    }

    fun showErrorDialog(context: Context) {
        progressDialog = MovieStoreProcessDialog(context, AnimationType.ERROR)
        progressDialog?.show()
        progressDialog?.listener = object : MovieStoreProcessDialog.MovieStoreAnimationListener {
            override fun finishAnimation() {
                progressDialog?.dismiss()
            }
        }
    }

    fun showUploadDialog(context: Context) {
        progressDialog = MovieStoreProcessDialog(context, AnimationType.UPLOAD)
        progressDialog?.show()
        progressDialog?.listener = object : MovieStoreProcessDialog.MovieStoreAnimationListener {
            override fun finishAnimation() {
                progressDialog?.dismiss()
            }
        }
    }

    fun showLoadingDialog(context: Context) {
        progressDialog = MovieStoreProcessDialog(context)
        progressDialog?.show()
    }

    fun stopDialog() {
        progressDialog?.dismiss()
    }
}