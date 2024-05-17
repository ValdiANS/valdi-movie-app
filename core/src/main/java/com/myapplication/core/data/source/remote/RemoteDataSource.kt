package com.myapplication.core.data.source.remote

import android.util.Log
import com.myapplication.core.data.source.local.entity.MovieEntity
import com.myapplication.core.data.source.remote.network.ApiResponse
import com.myapplication.core.data.source.remote.network.ApiService
import com.myapplication.core.data.source.remote.response.MovieDetailResponse
import com.myapplication.core.data.source.remote.response.MovieItemResponse
import com.myapplication.core.utils.DataMapper
import com.myapplication.core.utils.MovieType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {

    suspend fun getNowPlaying(): Flow<ApiResponse<List<MovieItemResponse>>> {
        return flow {
            try {
                val response = apiService.getNowPlaying()
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getPopular(): Flow<ApiResponse<List<MovieItemResponse>>> {
        return flow {
            try {
                val response = apiService.getPopular()
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUpcoming(): Flow<ApiResponse<List<MovieItemResponse>>> {
        return flow {
            try {
                val response = apiService.getUpcoming()
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getMovieDetail(id: Int): Flow<ApiResponse<MovieDetailResponse>> = flow {
        try {
            val response = apiService.getMovieDetail(id)

            emit(ApiResponse.Success(response))
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
            Log.e("RemoteDataSource", e.toString())
        }
    }.flowOn(Dispatchers.IO)
}

