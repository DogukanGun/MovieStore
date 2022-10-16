package com.dag.moviestore.ui.mainscreen.movies.domain

import com.dag.moviestore.base.domain.FlowUseCase
import com.dag.moviestore.network.BaseResponse
import com.dag.moviestore.network.BaseResult
import com.dag.moviestore.ui.mainscreen.movies.data.Movie
import com.dag.moviestore.ui.mainscreen.movies.data.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
): FlowUseCase<GetMoviesUseCase.GetMoviesUseCaseParams,BaseResult<List<Movie>>>() {


    data class GetMoviesUseCaseParams(
        var title:String,
        var plot:String
    )

    override fun buildUseCase(params: GetMoviesUseCaseParams): Flow<BaseResult<List<Movie>>> =
        movieRepository.getMovies(params.title,params.plot)
}