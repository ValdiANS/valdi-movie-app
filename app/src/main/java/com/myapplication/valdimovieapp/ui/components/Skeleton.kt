package com.myapplication.valdimovieapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.myapplication.valdimovieapp.ui.theme.ValdiMovieAppTheme
import com.myapplication.valdimovieapp.utils.shimmerLoadingAnimation

@Composable
fun SkeletonBox(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(Color.LightGray)
            .shimmerLoadingAnimation()
    )
}

@Composable
fun MovieCardSkeleton() {
    OutlinedCard(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .width(200.dp)
            .height(250.dp)
    ) {
        Column(
            modifier = Modifier.padding(4.dp)
        ) {

            SkeletonBox(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(Modifier.size(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            ) {
                Column {
                    SkeletonBox(
                        modifier = Modifier
                            .width(80.dp)
                            .height(16.dp)
                    )

                    Spacer(Modifier.size(4.dp))

                    SkeletonBox(
                        modifier = Modifier
                            .width(30.dp)
                            .height(16.dp)
                    )
                }

                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    SkeletonBox(
                        modifier = Modifier
                            .width(50.dp)
                            .height(16.dp)
                    )

                    Spacer(Modifier.size(4.dp))

                    SkeletonBox(
                        modifier = Modifier
                            .width(20.dp)
                            .height(16.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SkeletonPreview() {
    ValdiMovieAppTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SkeletonBox(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
            )

            MovieCardSkeleton()
        }
    }
}