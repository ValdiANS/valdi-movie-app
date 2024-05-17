package com.myapplication.valdimovieapp.di

import com.myapplication.core.domain.usecase.IMovieUseCase
import com.myapplication.core.domain.usecase.MovieUseCase
import com.myapplication.valdimovieapp.ui.pages.detailmovie.DetailMovieViewModel
import com.myapplication.valdimovieapp.ui.pages.favorite.FavoriteViewModel
import com.myapplication.valdimovieapp.ui.pages.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<IMovieUseCase> { MovieUseCase(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailMovieViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
}