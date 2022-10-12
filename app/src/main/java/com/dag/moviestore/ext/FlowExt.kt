package com.dag.moviestore.ext

import com.dag.moviestore.network.BaseResult
import kotlinx.coroutines.flow.*


inline fun <T> Flow<BaseResult<T>>.onSuccess(crossinline action: suspend (T?) -> Unit) = onEach {
    if (it is BaseResult.Success) {
        action(it.data)
    }
}

inline fun <T> Flow<BaseResult<T>>.onError(crossinline action: suspend (Exception) -> Unit) = onEach {
    if (it is BaseResult.Error) {
        action(it.exception)
    }
}

fun <T> Flow<BaseResult<T>>.retryIf(
    predicate: suspend FlowCollector<BaseResult<T>>.(exception: Throwable, attempt: Long) -> Boolean
): Flow<BaseResult<T>> = flow {
    var attempt = 0L
    var shallRetry: Boolean
    do {
        shallRetry = false
        val cause = emitUntilError(this)
        if (cause != null) {
            if (predicate(cause, attempt)) {
                shallRetry = true
                attempt++
            } else {
                emit(BaseResult.Error(cause))
            }
        }
    } while (shallRetry)
}

private suspend fun <T> Flow<BaseResult<T>>.emitUntilError(flowCollector: FlowCollector<BaseResult<T>>): Exception? {
    var resultException: Exception? = null
    takeWhile {
        resultException = (it as? BaseResult.Error)?.exception
        resultException == null
    }.collect { flowCollector.emit(it) }
    return resultException
}
