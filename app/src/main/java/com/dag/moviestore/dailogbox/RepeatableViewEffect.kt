package com.dag.moviestore.dailogbox

import com.dag.moviestore.base.ui.MovieStoreViewState
import kotlinx.coroutines.suspendCancellableCoroutine

interface RepeatableViewEffect: MovieStoreViewState {
    var onRepeatAction:((Boolean)->Unit)?
}

suspend fun MovieStoreViewState.shouldRetry():Boolean = suspendCancellableCoroutine { cancellableContinuation ->
    if (this is RepeatableViewEffect){
        onRepeatAction = {
            cancellableContinuation.resume(it,{})
        }
    }else {
        cancellableContinuation.resume(false,{})
    }
}