package com.myapplication.core.data.source.remote.network

import com.myapplication.core.data.source.remote.response.MovieDetailResponse
import com.myapplication.core.data.source.remote.response.NowPlayingResponse
import com.myapplication.core.data.source.remote.response.PopularResponse
import com.myapplication.core.data.source.remote.response.UpcomingResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("region") region: String = "",
    ): NowPlayingResponse

    @GET("movie/popular")
    suspend fun getPopular(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("region") region: String = "",
    ): PopularResponse

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("region") region: String = "",
    ): UpcomingResponse

    @GET("movie/{movieId}")
    suspend fun getMovieDetail(
        @Path("movieId") movieId: Int,
    ): MovieDetailResponse
}