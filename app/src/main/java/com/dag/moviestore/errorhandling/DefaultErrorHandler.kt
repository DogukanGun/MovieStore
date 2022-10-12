package com.dag.moviestore.errorhandling


import com.dag.moviestore.R
import com.dag.moviestore.base.ui.MovieStoreViewState
import com.dag.moviestore.dailogbox.createGenericDialog
import com.dag.moviestore.dailogbox.createRepeatableDialog
import com.dag.moviestore.network.*
import java.net.HttpURLConnection
import javax.inject.Inject

open class DefaultErrorHandler @Inject constructor() : ErrorHandler {

    override fun handle(throwable: Throwable): MovieStoreViewState? {
        if (throwable !is HomeNetworkFailure) {
            return null
        }

        return when (throwable) {
            NetworkConnectionFailure -> networkConnectionDialog()
            UnauthenticatedFailure -> networkConnectionDialog()
            SocketTimeoutFailure, is UnexpectedFailure -> createGenericDialog()
            is ApiFailure -> {
                if (throwable.response.code() == HttpURLConnection.HTTP_GATEWAY_TIMEOUT) {
                    createRepeatableDialog()
                } else {
                    networkConnectionDialog()
                }
            }
            else -> {null}
        }
    }
}

// TODO: Hata mesajları gösterilcek

fun networkConnectionDialog(): MovieStoreViewState = createGenericDialog(
    titleRes = R.string.network_error_message_title,
    messageRes = R.string.network_error_message_text,
)