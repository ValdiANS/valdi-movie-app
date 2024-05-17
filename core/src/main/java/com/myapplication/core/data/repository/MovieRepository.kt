package com.myapplication.core.data.repository

import com.myapplication.core.data.NetworkBoundResource
import com.myapplication.core.data.Resource
import com.myapplication.core.data.source.local.LocalDataSource
import com.myapplication.core.data.source.remote.RemoteDataSource
import com.myapplication.core.data.source.remote.network.ApiResponse
import com.myapplication.core.data.source.remote.response.MovieItemResponse
import com.myapplication.core.domain.model.MovieDetail
import com.myapplication.core.domain.model.MovieItem
import com.myapplication.core.domain.repository.IMovieRepository
import com.myapplication.core.utils.AppExecutors
import com.myapplication.core.utils.DataMapper
import com.myapplication.core.utils.MovieType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
) : IMovieRepository {

    override fun getNowPlaying(page: Int, region: String): Flow<Resource<List<MovieItem>>> =
        object : NetworkBoundResource<List<MovieItem>, List<MovieItemResponse>>() {
            override fun loadFromDb(): Flow<List<MovieItem>> {
                return localDataSource.getNowPlayingMovie().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<MovieItem>?): Boolean = data.isNullOrEmpty()


            override suspend fun createCall(): Flow<ApiResponse<List<MovieItemResponse>>> =
                remoteDataSource.getNowPlaying()

            override suspend fun saveCallResult(data: List<MovieItemResponse>) {
                val movieList = DataMapper.mapResponseToEntities(data, MovieType.NOW_PLAYING)
                localDataSource.insertMovie(movieList)
            }
        }.asFlow()

    override fun getPopular(page: Int, region: String): Flow<Resource<List<MovieItem>>> =
        object : NetworkBoundResource<List<MovieItem>, List<MovieItemResponse>>() {
            override fun loadFromDb(): Flow<List<MovieItem>> {
                return localDataSource.getPopularMovie().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<MovieItem>?): Boolean = data.isNullOrEmpty()


            override suspend fun createCall(): Flow<ApiResponse<List<MovieItemResponse>>> =
                remoteDataSource.getPopular()

            override suspend fun saveCallResult(data: List<MovieItemResponse>) {
                val movieList = DataMapper.mapResponseToEntities(data, MovieType.POPULAR)
                localDataSource.insertMovie(movieList)
            }
        }.asFlow()

    override fun getUpcoming(page: Int, region: String): Flow<Resource<List<MovieItem>>> =
        object : NetworkBoundResource<List<MovieItem>, List<MovieItemResponse>>() {
            override fun loadFromDb(): Flow<List<MovieItem>> {
                return localDataSource.getUpcomingMovie().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<MovieItem>?): Boolean = data.isNullOrEmpty()


            override suspend fun createCall(): Flow<ApiResponse<List<MovieItemResponse>>> =
                remoteDataSource.getUpcoming()

            override suspend fun saveCallResult(data: List<MovieItemResponse>) {
                val movieList = DataMapper.mapResponseToEntities(data, MovieType.UPCOMING)
                localDataSource.insertMovie(movieList)
            }
        }.asFlow()

    override fun getMovieDetail(id: Int): Flow<Resource<MovieDetail>> = flow {
        emit(Resource.Loading())

        val apiResponse = remoteDataSource.getMovieDetail(id).first()

        when (apiResponse) {
            is ApiResponse.Empty -> {}
            is ApiResponse.Error -> {
                emit(
                    Resource.Error(apiResponse.error)
                )
            }

            is ApiResponse.Success -> {
                emit(Resource.Success(DataMapper.mapMovieDetailResponseToDomain(apiResponse.data)))
            }
        }
    }

    override fun getMovieById(id: Int): Flow<Resource<MovieItem>> = flow {
        emit(Resource.Loading())

        val movie = localDataSource.getMovieById(id).first()
        emit(Resource.Success(DataMapper.mapEntityToDomain(movie)))
    }

    override fun bookmarkMovie(movieId: Int, state: Boolean) {
        appExecutors.diskIO().execute {
            localDataSource.bookmarkMovie(movieId, state)
        }
    }

    override fun getFavorite(): Flow<Resource<List<MovieItem>>> = flow {
        emit(Resource.Loading())

        val favoriteMovies = localDataSource.getFavoriteMovie().first()

        emit(Resource.Success(DataMapper.mapEntitiesToDomain(favoriteMovies)))
    }
}