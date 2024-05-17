package com.myapplication.valdimovieapp.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.myapplication.valdimovieapp.BuildConfig
import com.myapplication.valdimovieapp.R
import com.myapplication.valdimovieapp.ui.theme.ValdiMovieAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieCard(
    title: String,
    rating: String,
    releaseDate: String,
    thumbnailUrl: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val thumbnailImg = if (thumbnailUrl.isNullOrEmpty()) {
        R.drawable.placeholder_img
    } else {
        "${BuildConfig.IMAGE_BASE_URL}$thumbnailUrl"
    }

    val cardInteractionSource = remember { MutableInteractionSource() }
    val isPressed by cardInteractionSource.collectIsPressedAsState()
    val cardScale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        label = "cardScale"
    )

    /* LAYOUT */
    OutlinedCard(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .width(200.dp)
            .height(250.dp)
            .graphicsLayer {
                scaleX = cardScale
                scaleY = cardScale
            },
//            .scale(cardScale),
        onClick = onClick,
        interactionSource = cardInteractionSource
    ) {
        Column(
            modifier = Modifier.padding(4.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(thumbnailImg)
                    .placeholder(R.drawable.placeholder_img)
                    .error(R.drawable.error_img)
                    .crossfade(true)
                    .build(),
                contentDescription = title,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .weight(1f),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.size(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                }

                Spacer(Modifier.size(4.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = rating,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 10.sp,
                        ),
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = releaseDate,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 10.sp,
                        ),
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieCardPreview() {
    ValdiMovieAppTheme {
        MovieCard(
            title = "title",
            rating = "rating",
            releaseDate = "releaseDate",
            thumbnailUrl = "/7lTnXOy0iNtBAdRP3TZvaKJ77F6.jpg",
            onClick = {}
        )
    }
}