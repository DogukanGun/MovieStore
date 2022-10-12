package com.dag.moviestore.network

import java.lang.Exception

sealed class BaseResult<out T>{
    data class Success<out T>(val data:T?): BaseResult<T>()
    data class Error(val exception: Exception): BaseResult<Nothing>()
    object Loading: BaseResult<Nothing>()
}

fun <T,R> BaseResult<T>.map(action:(T?)->R?): BaseResult<R> {
    return when(this){
        is BaseResult.Success -> BaseResult.Success(action.invoke(data))
        is BaseResult.Loading -> BaseResult.Loading
        is BaseResult.Error -> BaseResult.Error(exception)
    }
}

suspend fun <T,R> BaseResult<T>.mapSuspend(action:suspend (T?)->R?): BaseResult<R> {
    return when(this){
        is BaseResult.Success -> BaseResult.Success(action.invoke(data))
        is BaseResult.Loading -> BaseResult.Loading
        is BaseResult.Error -> BaseResult.Error(exception)
    }
}

fun <T,R> BaseResult<T>.mapValue(action:(T)->R?):R?{
    return when(this){
        is BaseResult.Success ->{
            if (data!=null){
                action.invoke(data)
            }
            else
                null
        }
        is BaseResult.Loading -> null
        is BaseResult.Error -> throw exception
    }
}

fun <T> BaseResult<T>.getData():T? = if (this is BaseResult.Success) data else null
