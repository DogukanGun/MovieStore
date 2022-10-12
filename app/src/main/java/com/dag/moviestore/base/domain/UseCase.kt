package com.dag.moviestore.base.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

abstract class UseCase<in P, R>(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    protected abstract suspend fun buildUseCase(params: P): R

    suspend fun execute(params: P): R = withContext(dispatcher) {
        buildUseCase(params)
    }
}

abstract class FlowUseCase<in P, R>(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    protected abstract fun buildUseCase(params: P): Flow<R>

    fun observe(params: P): Flow<R> = buildUseCase(params).flowOn(dispatcher)
}

object None
