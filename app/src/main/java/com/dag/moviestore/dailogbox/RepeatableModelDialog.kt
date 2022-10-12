package com.dag.moviestore.dailogbox

import com.dag.moviestore.base.ui.MovieStoreViewState

interface RepeatableModelDialog: MovieStoreViewState, RepeatableViewEffect, ModelDialog {
    val repeatOnPositiveButtonClick:Boolean
    val repeatOnNegativeButtonClick:Boolean
    val repeatOnCancel:Boolean
}