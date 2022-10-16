package com.dag.moviestore.base.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.dag.moviestore.errorhandling.DefaultErrorHandler
import com.dag.moviestore.errorhandling.ErrorHandler
import com.dag.moviestore.ext.onSuccess
import com.dag.moviestore.ext.retryIf
import com.dag.moviestore.ext.shouldRetry
import com.dag.moviestore.network.BaseResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class MovieStoreViewModel: ViewModel(){

    var viewState = MutableLiveData<MovieStoreViewState>()
    private val loading = MutableLiveData<Boolean>(false)

    protected fun <T> Flow<BaseResult<T>>.publishLoading() = onEach {
        updateLoading(it is BaseResult.Loading)
    }

    private fun updateLoading(isLoading: Boolean) {
        this.loading.value = isLoading
    }

    fun isLoading() = loading.distinctUntilChanged()

    private fun postViewEffect(effect: MovieStoreViewState) {
        viewState.value = effect
    }

    private fun <T> Flow<BaseResult<T>>.handleErrorWith(
        errorHandler: ErrorHandler?
    ): Flow<BaseResult<T>> = retryIf { exception, _ ->
        val effect = errorHandler?.handle(exception)
        effect?.let {
            postViewEffect(it)
            it.shouldRetry()
        } ?: false
    }

    protected fun <T> Flow<T>.subscribe() = launchIn(viewModelScope)

    protected fun <T> Flow<BaseResult<T>>.subscribe(
        errorHandler: ErrorHandler
    ) = handleErrorWith(errorHandler)
        .subscribe()

    protected fun <T> Flow<BaseResult<T>>.subscribe(
        errorHandler: ErrorHandler? = DefaultErrorHandler(),
        onSuccess: (T?) -> Unit
    ) = handleErrorWith(errorHandler)
        .onSuccess {
            onSuccess.invoke(it)
        }
        .subscribe()

    protected fun <T> Flow<BaseResult<T>>.subscribeNotNull(
        errorHandler: ErrorHandler = DefaultErrorHandler(),
        onSuccess: (T) -> Unit
    ) = subscribe(errorHandler) { result ->
        result?.let {
            onSuccess.invoke(it)
        }
    }
}