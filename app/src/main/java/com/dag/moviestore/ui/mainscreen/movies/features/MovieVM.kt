package com.dag.moviestore.ui.mainscreen.movies.features

import androidx.compose.runtime.mutableStateOf
import com.dag.moviestore.base.ui.MovieStoreViewModel
import com.dag.moviestore.ui.mainscreen.movies.domain.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieVM @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
): MovieStoreViewModel() {

    val movieState = mutableStateOf(MovieState())

    fun getMovies(getMoviesUseCaseParams: GetMoviesUseCase.GetMoviesUseCaseParams){
        getMoviesUseCase.observe(getMoviesUseCaseParams)
            .publishLoading()
            .subscribe {
                it?.let { movies->
                    movieState.value.movies.value = movies
                }
            }
    }
}