package com.dag.moviestore.network

import com.dag.moviestore.dailogbox.DialogBoxModel
import retrofit2.Response
import java.io.IOException

sealed class HomeNetworkFailure : IOException()

object NetworkConnectionFailure : HomeNetworkFailure()

object UnauthenticatedFailure : HomeNetworkFailure()

object SocketTimeoutFailure : HomeNetworkFailure()

data class ApiFailure(val response: Response<*>) : HomeNetworkFailure()

data class DialogFailure(val dialogBox: DialogBoxModel?): HomeNetworkFailure()

data class UnexpectedFailure(val throwable: Throwable) : HomeNetworkFailure()
