package com.dag.moviestore.dailogbox

import androidx.annotation.StringRes

data class ModelDialogButton(
    @StringRes val textRes: Int,
    val onClick:(()->Unit)? = null
)
