package com.dag.moviestore.network

import kotlinx.coroutines.flow.flow

open class BaseRepository {
    protected fun <T> fetch(apiCall:suspend () -> BaseResult<T>) = flow {
        emit(BaseResult.Loading)
        val response = apiCall()
        emit(response)
    }
}