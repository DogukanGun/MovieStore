package com.dag.moviestore.network.calladapter

import com.dag.moviestore.network.*
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.UnsupportedOperationException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException

class NetworkResponseCall<S:Any>(
    private val delegate: Call<S>
) : Call<BaseResult<S>> {

    override fun enqueue(callback: Callback<BaseResult<S>>) {
        return delegate.enqueue(object :Callback<S>{
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()
                val networkResponse = when(response.code()){
                    in 200..299 -> BaseResult.Success(body)
                    401 -> BaseResult.Error(UnauthenticatedFailure)
                    else -> BaseResult.Error(ApiFailure(response))
                }
                callback.onResponse(this@NetworkResponseCall, Response.success(networkResponse))
            }

            override fun onFailure(call: Call<S>, t: Throwable) {
                val networkResponse = when(t){
                    is HomeNetworkFailure -> BaseResult.Error(t)
                    is ConnectException, is SocketException -> BaseResult.Error(
                        NetworkConnectionFailure
                    )
                    is SocketTimeoutException -> BaseResult.Error(SocketTimeoutFailure)
                    else -> BaseResult.Error(UnexpectedFailure(t))
                }
                callback.onResponse(this@NetworkResponseCall,Response.success(networkResponse))
            }

        })
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun clone(): Call<BaseResult<S>> = NetworkResponseCall(delegate.clone())

    override fun execute(): Response<BaseResult<S>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()


}