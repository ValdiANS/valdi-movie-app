package com.myapplication.core.domain.usecase

import com.myapplication.core.data.Resource
import com.myapplication.core.domain.model.MovieDetail
import com.myapplication.core.domain.model.MovieItem
import kotlinx.coroutines.flow.Flow

interface IMovieUseCase {
    fun getNowPlayingMovies(): Flow<Resource<List<MovieItem>>>
    fun getPopularMovies(): Flow<Resource<List<MovieItem>>>
    fun getUpcomingMovies(): Flow<Resource<List<MovieItem>>>
    fun getMovieDetail(id: Int): Flow<Resource<MovieDetail>>
    fun getMovieById(id: Int): Flow<Resource<MovieItem>>
    fun bookmarkMovie(id: Int, state: Boolean)
    fun getFavoriteMovies(): Flow<Resource<List<MovieItem>>>
}