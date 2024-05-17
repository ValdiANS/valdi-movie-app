package com.myapplication.valdimovieapp

import android.app.Application
import com.myapplication.core.di.databaseModule
import com.myapplication.core.di.networkModule
import com.myapplication.core.di.repositoryModule
import com.myapplication.valdimovieapp.di.useCaseModule
import com.myapplication.valdimovieapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

open class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MainApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}