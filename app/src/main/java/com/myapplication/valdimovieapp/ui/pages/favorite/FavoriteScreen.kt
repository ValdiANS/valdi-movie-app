package com.myapplication.valdimovieapp.ui.pages.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.myapplication.core.data.Resource
import com.myapplication.core.domain.model.MovieItem
import com.myapplication.valdimovieapp.ui.components.MovieCard
import com.myapplication.valdimovieapp.ui.theme.ValdiMovieAppTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoriteScreen(
    onDetailClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    favoriteViewModel: FavoriteViewModel = koinViewModel(),
) {
    val lifecycle = LocalLifecycleOwner.current

    var isLoadingFavoriteMovies by remember { mutableStateOf(false) }
    var favoriteMovies by rememberSaveable { mutableStateOf(emptyList<MovieItem>()) }

    LaunchedEffect(true) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            launch {
                favoriteViewModel.favoriteMovies.collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            isLoadingFavoriteMovies = false
                            favoriteMovies = result.data ?: emptyList()
                        }

                        is Resource.Error -> {}

                        is Resource.Loading -> {
                            isLoadingFavoriteMovies = true
                        }
                    }
                }
            }
        }
    }

    /* LAYOUT */
    if (isLoadingFavoriteMovies) {
        Text(text = "Loading . . .")
    } else {
        FavoriteMoviesContent(
            favoriteMovies = favoriteMovies,
            onDetailClick = onDetailClick,
            modifier = modifier
        )
    }
}

@Composable
fun FavoriteMoviesContent(
    favoriteMovies: List<MovieItem>,
    onDetailClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    if (favoriteMovies.isEmpty()) {
        Text(
            text = "No Favorite Movies . . .",
            textAlign = TextAlign.Center
        )

        return
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        items(favoriteMovies.size) {
            MovieCard(
                title = favoriteMovies[it].title,
                rating = favoriteMovies[it].voteAverage.toString(),
                releaseDate = favoriteMovies[it].releaseDate,
                thumbnailUrl = favoriteMovies[it].posterPath,
                onClick = {
                    onDetailClick(favoriteMovies[it].id.toString())
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoriteScreenPreview() {
    ValdiMovieAppTheme {
        FavoriteScreen(
            onDetailClick = {}
        )
    }
}