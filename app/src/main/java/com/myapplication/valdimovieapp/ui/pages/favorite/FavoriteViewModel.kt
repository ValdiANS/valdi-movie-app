package com.myapplication.valdimovieapp.ui.pages.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapplication.core.data.Resource
import com.myapplication.core.domain.model.MovieItem
import com.myapplication.core.domain.usecase.IMovieUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class FavoriteViewModel(
    movieUseCase: IMovieUseCase,
) : ViewModel() {
    val favoriteMovies: StateFlow<Resource<List<MovieItem>>> =
        movieUseCase.getFavoriteMovies().stateIn(
            initialValue = Resource.Loading(),
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000)
        )
}