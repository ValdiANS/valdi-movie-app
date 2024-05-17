package com.myapplication.core.data.source.local

import com.myapplication.core.data.source.local.entity.MovieEntity
import com.myapplication.core.data.source.local.room.MovieDao
import com.myapplication.core.utils.MovieType
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val movieDao: MovieDao) {

    fun getAllMovie(): Flow<List<MovieEntity>> = movieDao.getAllMovie()

    fun getNowPlayingMovie(): Flow<List<MovieEntity>> = movieDao.getAllMovieOfType(MovieType.NOW_PLAYING)

    fun getPopularMovie(): Flow<List<MovieEntity>> = movieDao.getAllMovieOfType(MovieType.POPULAR)

    fun getUpcomingMovie(): Flow<List<MovieEntity>> = movieDao.getAllMovieOfType(MovieType.UPCOMING)

    fun getFavoriteMovie(): Flow<List<MovieEntity>> = movieDao.getFavoriteMovie()

    fun getMovieById(movieId: Int): Flow<MovieEntity> = movieDao.getMovieById(movieId)

    suspend fun insertMovie(tourismList: List<MovieEntity>) = movieDao.insertMovie(tourismList)

    fun bookmarkMovie(movieId: Int, newState: Boolean) {
        movieDao.bookmarkMovie(movieId, newState)
    }
}