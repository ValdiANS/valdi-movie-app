package com.myapplication.valdimovieapp.ui.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import com.myapplication.core.data.Resource
import com.myapplication.core.domain.model.MovieItem
import com.myapplication.core.domain.usecase.IMovieUseCase

class HomeViewModel(
    movieUseCase: IMovieUseCase
) : ViewModel() {
    val nowPlayingMovies: StateFlow<Resource<List<MovieItem>>> =
        movieUseCase.getNowPlayingMovies().stateIn(
            initialValue = Resource.Loading(),
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000)
        )

    val popularMovies: StateFlow<Resource<List<MovieItem>>> =
        movieUseCase.getPopularMovies().stateIn(
            initialValue = Resource.Loading(),
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000)
        )

    val upcomingMovies: StateFlow<Resource<List<MovieItem>>> =
        movieUseCase.getUpcomingMovies().stateIn(
            initialValue = Resource.Loading(),
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000)
        )
}