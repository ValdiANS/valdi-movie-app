package com.myapplication.core.di

import androidx.room.Room
import com.myapplication.core.BuildConfig
import com.myapplication.core.data.repository.MovieRepository
import com.myapplication.core.data.source.local.LocalDataSource
import com.myapplication.core.data.source.local.room.MovieDatabase
import com.myapplication.core.data.source.remote.RemoteDataSource
import com.myapplication.core.data.source.remote.network.ApiService
import com.myapplication.core.domain.repository.IMovieRepository
import com.myapplication.core.utils.AppExecutors
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<MovieDatabase>().movieDao() }

    single {
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java,
            "Movie.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        val authInterceptor = Interceptor { chain ->
            val req = chain.request()
            val requestHeaders = req.newBuilder()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer ${BuildConfig.API_TOKEN}")
                .build()

            chain.proceed(requestHeaders)
        }

        val client = OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.MINUTES)
            .readTimeout(2, TimeUnit.MINUTES)
            .writeTimeout(2, TimeUnit.MINUTES)
            .addInterceptor(authInterceptor)
            .build()

        client
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()

        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IMovieRepository> {
        MovieRepository(get(), get(), get())
    }
}