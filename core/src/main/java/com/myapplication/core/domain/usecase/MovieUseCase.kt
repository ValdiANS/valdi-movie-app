package com.myapplication.core.domain.usecase

import com.myapplication.core.data.Resource
import com.myapplication.core.domain.model.MovieDetail
import com.myapplication.core.domain.model.MovieItem
import com.myapplication.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieUseCase(private val movieRepository: IMovieRepository) : IMovieUseCase {
    override fun getNowPlayingMovies(): Flow<Resource<List<MovieItem>>> = movieRepository.getNowPlaying()

    override fun getPopularMovies(): Flow<Resource<List<MovieItem>>> = movieRepository.getPopular()

    override fun getUpcomingMovies(): Flow<Resource<List<MovieItem>>> = movieRepository.getUpcoming()

    override fun getMovieDetail(id: Int): Flow<Resource<MovieDetail>> = movieRepository.getMovieDetail(id)

    override fun getMovieById(id: Int): Flow<Resource<MovieItem>> = movieRepository.getMovieById(id)

    override fun bookmarkMovie(id: Int, state: Boolean) = movieRepository.bookmarkMovie(id, state)

    override fun getFavoriteMovies(): Flow<Resource<List<MovieItem>>> = movieRepository.getFavorite()
}