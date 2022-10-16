package com.dag.moviestore.ui.mainscreen.movies.data

import com.dag.moviestore.network.BaseRepository
import com.dag.moviestore.network.MovieStoreService
import com.dag.moviestore.network.getData
import com.dag.moviestore.network.getDataAsResultBaseResult
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val service: MovieStoreService,
) : BaseRepository() {

    fun getMovies(title:String,plot:String) = fetch {
        service.getMovies(title,plot).getDataAsResultBaseResult()
    }
}