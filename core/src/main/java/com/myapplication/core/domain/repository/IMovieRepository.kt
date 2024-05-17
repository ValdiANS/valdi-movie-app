package com.myapplication.core.domain.repository

import com.myapplication.core.data.Resource
import com.myapplication.core.domain.model.MovieDetail
import com.myapplication.core.domain.model.MovieItem
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getNowPlaying(
        page: Int = 1,
        region: String = "ID",
    ): Flow<Resource<List<MovieItem>>>

    fun getPopular(
        page: Int = 1,
        region: String = "ID",
    ): Flow<Resource<List<MovieItem>>>

    fun getUpcoming(
        page: Int = 1,
        region: String = "ID",
    ): Flow<Resource<List<MovieItem>>>

    fun getMovieDetail(
        id: Int
    ): Flow<Resource<MovieDetail>>

    fun getMovieById(
        id: Int
    ): Flow<Resource<MovieItem>>

    fun bookmarkMovie(movieId: Int, state: Boolean)

    fun getFavorite(): Flow<Resource<List<MovieItem>>>
}