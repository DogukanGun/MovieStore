package com.dag.moviestore.ext

import com.dag.moviestore.base.ui.MovieStoreViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine

interface RepeatableRentViewEffect : MovieStoreViewState {

    var onRepeatAction: ((Boolean) -> Unit)?
}

@OptIn(ExperimentalCoroutinesApi::class)
suspend fun MovieStoreViewState.shouldRetry(): Boolean = suspendCancellableCoroutine { continuation ->
    if (this is RepeatableRentViewEffect) {
        onRepeatAction = {
            continuation.resume(it,null)
        }
    } else {
        continuation.resume(false,null)
    }
}