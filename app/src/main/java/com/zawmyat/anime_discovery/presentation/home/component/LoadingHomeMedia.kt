package com.zawmyat.anime_discovery.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zawmyat.anime_discovery.R
import com.zawmyat.anime_discovery.data.utils.format
import com.zawmyat.anime_discovery.data.utils.toAnnotatedString
import com.zawmyat.anime_discovery.presentation.component.getGenreColor
import com.zawmyat.anime_discovery.presentation.component.shimmerLoadingAnimation

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LoadingHomeMedia() {

    LazyRow(
        modifier = Modifier.padding(horizontal = 12.dp),
        userScrollEnabled = false
    ) {
        items(10) {
            Box(
                modifier = Modifier
                    .width(325.dp)
                    .height(350.dp)
                    .padding(end = 12.dp)
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {

                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                        ) {

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .background(
                                        brush = Brush.verticalGradient(
                                            colors = listOf(
                                                Color.Transparent,
                                                MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                                                MaterialTheme.colorScheme.surface,
                                            )
                                        )
                                    ),
                                contentAlignment = Alignment.BottomStart
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(all = 12.dp)

                                ) {

                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(5.dp))
                                            .height(150.dp)
                                            .width(90.dp)
                                            .background(color = Color.LightGray)
                                            .shimmerLoadingAnimation()
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(150.dp),
                                        verticalArrangement = Arrangement.Bottom
                                    ) {

                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(3.dp))
                                                .fillMaxWidth()
                                                .height(20.dp)
                                                .background(Color.LightGray)
                                                .shimmerLoadingAnimation()
                                        )

                                        Spacer(modifier = Modifier.height(8.dp))

                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(3.dp))
                                                .width(100.dp)
                                                .height(20.dp)
                                                .background(Color.LightGray)
                                                .shimmerLoadingAnimation()
                                        )

                                        Spacer(modifier = Modifier.height(8.dp))

                                        Row {
                                            //Rating
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(3.dp))
                                                    .width(50.dp)
                                                    .height(20.dp)
                                                    .background(Color.LightGray)
                                                    .shimmerLoadingAnimation()
                                            )

                                            Spacer(modifier = Modifier.width(6.dp))
                                            //Favorite
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(3.dp))
                                                    .width(50.dp)
                                                    .height(20.dp)
                                                    .background(Color.LightGray)
                                                    .shimmerLoadingAnimation()
                                            )

                                            Spacer(modifier = Modifier.width(6.dp))

                                            //Year
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(3.dp))
                                                    .width(50.dp)
                                                    .height(20.dp)
                                                    .background(Color.LightGray)
                                                    .shimmerLoadingAnimation()
                                            )

                                        }

                                    }
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                    ) {
                        for(i in 1..3) {
                            Box(
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(3.dp))
                                        .width(40.dp)
                                        .height(20.dp)
                                        .background(Color.LightGray)
                                        .shimmerLoadingAnimation()
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Column(
                        modifier = Modifier.padding(horizontal = 12.dp)
                    ) {
                        for(i in 1..4) {
                            Box(modifier = Modifier.padding(bottom = 8.dp)) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(3.dp))
                                        .fillMaxWidth()
                                        .height(20.dp)
                                        .background(Color.LightGray)
                                        .shimmerLoadingAnimation()
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                }
            }
        }
    }
}