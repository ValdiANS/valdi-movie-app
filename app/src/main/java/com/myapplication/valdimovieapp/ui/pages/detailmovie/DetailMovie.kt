package com.myapplication.valdimovieapp.ui.pages.detailmovie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.myapplication.core.data.Resource
import com.myapplication.core.domain.model.MovieDetail
import com.myapplication.valdimovieapp.BuildConfig
import com.myapplication.valdimovieapp.R
import com.myapplication.valdimovieapp.ui.theme.ValdiMovieAppTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMovie(
    movieId: String,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    detailMovieViewModel: DetailMovieViewModel = koinViewModel(),
) {
    val lifecycle = LocalLifecycleOwner.current
    var isMovieBookmarked by remember {
        mutableStateOf(false)
    }

    var isLoadingMovieDetail by remember { mutableStateOf(true) }
    var movieDetail by remember { mutableStateOf<MovieDetail?>(null) }

    val onBookmarked = {
        detailMovieViewModel.bookmarkMovie(movieId.toInt(), !isMovieBookmarked)
        isMovieBookmarked = !isMovieBookmarked
    }

    LaunchedEffect(true) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch {
                detailMovieViewModel.getMovieDetail(movieId.toInt()).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            isLoadingMovieDetail = false
                            movieDetail = result.data
                        }

                        is Resource.Error -> {}

                        is Resource.Loading -> {
                            isLoadingMovieDetail = true
                        }
                    }
                }
            }

            launch {
                detailMovieViewModel.getMovieItemFromDatabase(movieId.toInt()).collect {result ->
                    when(result) {
                        is Resource.Error -> {}
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            isMovieBookmarked = result.data?.isFavorite == true
                        }
                    }
                }
            }
        }
    }

    if (isLoadingMovieDetail) {
        Text(text = "Loading . . .")
    } else {
        DetailMovieContent(
            isMovieBookmarked = isMovieBookmarked,
            movieDetail = movieDetail,
            onNavigateBack = onNavigateBack,
            onBookmarked = onBookmarked,
            modifier = modifier
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMovieContent(
    isMovieBookmarked: Boolean,
    modifier: Modifier = Modifier,
    movieDetail: MovieDetail? = null,
    onNavigateBack: () -> Unit,
    onBookmarked: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        rememberTopAppBarState()
    ),
) {

    val thumbnailImg = if (movieDetail?.posterPath.isNullOrEmpty()) {
        R.drawable.placeholder_img
    } else {
        "${BuildConfig.IMAGE_BASE_URL}${movieDetail?.posterPath}"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = movieDetail?.title ?: "Placeholder Title",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateBack
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back_content_description)
                        )
                    }

                },
                actions = {
                    IconButton(
                        onClick = {
                            // Add movie to bookmarks
                            onBookmarked()
                        }
                    ) {
                        Icon(
                            painter = if (isMovieBookmarked) {
                                painterResource(R.drawable.baseline_bookmark_24)
                            } else {
                                painterResource(R.drawable.baseline_bookmark_border_24)
                            },
                            contentDescription = if (isMovieBookmarked) {
                                stringResource(R.string.delete_from_bookmark_content_description)
                            } else {
                                stringResource(R.string.bookmark_content_description)
                            }
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(thumbnailImg)
                    .crossfade(true)
                    .placeholder(R.drawable.placeholder_img)
                    .error(R.drawable.error_img)
                    .build(),
                contentDescription = "beef.name",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )

            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Column(modifier = modifier) {
                    Text(
                        text = movieDetail?.title ?: "Placeholder Title",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )

                    Text(
                        text = movieDetail?.genres
                            ?.map { it.name }
                            ?.joinToString(", ")
                            .toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    if (movieDetail?.productionCountries != null && movieDetail.productionCountries!!.isNotEmpty()) {
                        Text(
                            text = "Country: " + movieDetail.productionCountries!![0].name,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    if (movieDetail?.productionCompanies != null && movieDetail.productionCompanies!!.isNotEmpty()) {
                        Text(
                            text = "Production Companies: " + movieDetail.productionCompanies
                                ?.map { it.name }
                                ?.joinToString(", ")
                                .toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                Text(
                    text = movieDetail?.overview.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailMoviePreview() {
    ValdiMovieAppTheme {
        DetailMovie(
            movieId = "asd",
            onNavigateBack = {},
        )
    }
}