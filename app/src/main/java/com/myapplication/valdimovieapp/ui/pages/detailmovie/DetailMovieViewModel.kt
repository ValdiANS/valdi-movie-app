package com.myapplication.valdimovieapp.ui.pages.detailmovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapplication.core.data.Resource
import com.myapplication.core.domain.model.MovieDetail
import com.myapplication.core.domain.model.MovieItem
import com.myapplication.core.domain.usecase.IMovieUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn

class DetailMovieViewModel(
    private val movieUseCase: IMovieUseCase
) : ViewModel() {
    fun getMovieDetail(id: Int): StateFlow<Resource<MovieDetail>> =
        movieUseCase.getMovieDetail(id).stateIn(
            initialValue = Resource.Loading(),
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000)
        )

//    suspend fun isMovieBookmarked(id: Int): Boolean =
//        movieUseCase.getMovieById(id).first().data?.isFavorite == true

    fun getMovieItemFromDatabase(id:Int): StateFlow<Resource<MovieItem>> =
        movieUseCase.getMovieById(id).stateIn(
            initialValue = Resource.Loading(),
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000)
        )

    fun bookmarkMovie(id: Int, state: Boolean) {
        movieUseCase.bookmarkMovie(id, state)
    }
}