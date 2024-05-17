package com.myapplication.valdimovieapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myapplication.core.data.source.remote.response.MovieItemResponse
import com.myapplication.core.domain.model.MovieItem
import com.myapplication.valdimovieapp.ui.theme.ValdiMovieAppTheme
import com.myapplication.valdimovieapp.ui.theme.poppinsFamily

@Composable
fun MovieCardRow(
    title: String,
    movieList: List<MovieItem>,
    onDetailClick: (String) -> Unit,
    onSeeMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = poppinsFamily,
                    fontWeight = FontWeight.Bold
                )
            )

//            TextButton(
//                onClick = { /*TODO*/ },
//                modifier = Modifier.align(Alignment.CenterVertically)
//            ) {
//                Text(
//                    text = "See more",
//                    style = MaterialTheme.typography.bodySmall.copy(
//                        fontFamily = poppinsFamily,
//                        fontWeight = FontWeight.Light
//                    )
//                )
//            }
        }

        Spacer(Modifier.size(12.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 24.dp)
        ) {

            if (isLoading) {
                items(3) {
                    MovieCardSkeleton()
                }
            } else {
                items(movieList) {
                    MovieCard(
                        title = it.title,
                        rating = it.voteAverage.toString(),
                        releaseDate = it.releaseDate,
                        thumbnailUrl = it.posterPath,
                        onClick = {
                                onDetailClick(it.id.toString())
                        }
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieCardRowPreview() {
    ValdiMovieAppTheme {
        MovieCardRow(
            title = "Now Playing",
            movieList = listOf(
                MovieItem(
                    backdropPath = "/yOm993lsJyPmBodlYjgpPwBjXP9.jpg",
                    id = 787699,
                    overview = "Willy Wonka – chock-full of ideas and determined to change the world one delectable bite at a time – is proof that the best things in life begin with a dream, and if you’re lucky enough to meet Willy Wonka, anything is possible.",
                    posterPath = "/qhb1qOilapbapxWQn9jtRCMwXJF.jpg",
                    releaseDate = "2023-12-06",
                    title = "Wonka 1",
                    voteAverage = 7.193,
                ),
                MovieItem(
                    backdropPath = "/yOm993lsJyPmBodlYjgpPwBjXP9.jpg",
                    id = 787699,
                    overview = "Willy Wonka – chock-full of ideas and determined to change the world one delectable bite at a time – is proof that the best things in life begin with a dream, and if you’re lucky enough to meet Willy Wonka, anything is possible.",
                    posterPath = "/qhb1qOilapbapxWQn9jtRCMwXJF.jpg",
                    releaseDate = "2023-12-06",
                    title = "Wonka 3",
                    voteAverage = 7.193,
                ),
                MovieItem(
                    backdropPath = "/yOm993lsJyPmBodlYjgpPwBjXP9.jpg",
                    id = 787699,
                    overview = "Willy Wonka – chock-full of ideas and determined to change the world one delectable bite at a time – is proof that the best things in life begin with a dream, and if you’re lucky enough to meet Willy Wonka, anything is possible.",
                    posterPath = "/qhb1qOilapbapxWQn9jtRCMwXJF.jpg",
                    releaseDate = "2023-12-06",
                    title = "Wonka 2",
                    voteAverage = 7.193,
                )
            ),
            isLoading = false,
            onSeeMoreClick = {},
            onDetailClick = {}
        )
    }
}