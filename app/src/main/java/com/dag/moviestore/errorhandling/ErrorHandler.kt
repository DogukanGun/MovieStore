package com.dag.moviestore.errorhandling

import com.dag.moviestore.base.ui.MovieStoreViewState


interface ErrorHandler {

    fun handle(throwable: Throwable): MovieStoreViewState?
}