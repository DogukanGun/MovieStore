package com.dag.moviestore.ui.mainscreen.movies.features

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.dag.moviestore.ui.mainscreen.movies.data.Movie

data class MovieState(
    var movies:MutableState<List<Movie>> = mutableStateOf(emptyList())
)
