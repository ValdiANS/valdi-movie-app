package com.myapplication.valdimovieapp.ui.pages.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.myapplication.core.data.Resource
import com.myapplication.core.data.source.remote.response.MovieItemResponse
import com.myapplication.core.domain.model.MovieItem
import com.myapplication.valdimovieapp.ui.components.MovieCardRow
import com.myapplication.valdimovieapp.ui.theme.ValdiMovieAppTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    onDetailClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = koinViewModel(),
) {
    val lifecycle = LocalLifecycleOwner.current

    var isLoadingNowPlaying by remember { mutableStateOf(true) }
    var isLoadingPopular by remember { mutableStateOf(true) }
    var isLoadingUpcoming by remember { mutableStateOf(true) }

    var nowPlayingMovieList by rememberSaveable { mutableStateOf(emptyList<MovieItem>()) }
    var popularMovieList by rememberSaveable { mutableStateOf(emptyList<MovieItem>()) }
    var upcomingMovieList by rememberSaveable { mutableStateOf(emptyList<MovieItem>()) }

    LaunchedEffect(true) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                homeViewModel.nowPlayingMovies.collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            isLoadingNowPlaying = false
                            nowPlayingMovieList = result.data ?: emptyList()
                        }

                        is Resource.Error -> {}

                        is Resource.Loading -> {
                            isLoadingNowPlaying = true
                        }
                    }
                }
            }

            launch {
                homeViewModel.popularMovies.collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            isLoadingPopular = false
                            popularMovieList = result.data ?: emptyList()
                        }

                        is Resource.Error -> {}

                        is Resource.Loading -> {
                            isLoadingPopular = true
                        }
                    }
                }
            }

            launch {
                homeViewModel.upcomingMovies.collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            isLoadingUpcoming = false
                            upcomingMovieList = result.data ?: emptyList()
                        }

                        is Resource.Error -> {}

                        is Resource.Loading -> {
                            isLoadingUpcoming = true
                        }
                    }
                }
            }
        }
    }

    /* LAYOUT */
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
    ) {
        item {
            MovieCardRow(title = "Now Playing",
                movieList = nowPlayingMovieList,
                isLoading = isLoadingNowPlaying,
                onSeeMoreClick = {},
                onDetailClick = onDetailClick
            )
        }

        item {
            MovieCardRow(title = "Popular",
                movieList = popularMovieList,
                isLoading = isLoadingPopular,
                onSeeMoreClick = {},
                onDetailClick = onDetailClick
            )
        }

        item {
            MovieCardRow(title = "Upcoming",
                movieList = upcomingMovieList,
                isLoading = isLoadingUpcoming,
                onSeeMoreClick = {},
                onDetailClick = onDetailClick
            )
        }

        item {
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ValdiMovieAppTheme {
        HomeScreen(onDetailClick = {})
    }
}