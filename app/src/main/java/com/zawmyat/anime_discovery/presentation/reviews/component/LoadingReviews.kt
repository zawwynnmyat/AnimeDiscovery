package com.zawmyat.anime_discovery.presentation.reviews.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zawmyat.anime_discovery.R
import com.zawmyat.anime_discovery.data.utils.format
import com.zawmyat.anime_discovery.presentation.component.shimmerLoadingAnimation

@Composable
fun LoadingReviews() {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        for(i in 1..10) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentAlignment = Alignment.Center
            ) {


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(color = Color.Black.copy(0.3f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(all = 8.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Row() {
                                //User Avatar
                                Box(
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .height(55.dp)
                                        .width(55.dp)
                                        .background(color = Color.LightGray)
                                        .shimmerLoadingAnimation(),
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                Column {

                                    //Review Ttile
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .height(20.dp)
                                            .fillMaxWidth()
                                            .background(color = Color.LightGray)
                                            .shimmerLoadingAnimation(),
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    //Timestamp
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .height(20.dp)
                                            .width(100.dp)
                                            .background(color = Color.LightGray)
                                            .shimmerLoadingAnimation(),
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            //Summary
                            for(i in 1..2) {
                                Box(modifier = Modifier.padding(bottom = 8.dp)) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(4.dp))
                                            .height(20.dp)
                                            .fillMaxWidth()
                                            .background(color = Color.LightGray)
                                            .shimmerLoadingAnimation(),
                                    )
                                }
                            }

                        }


                        //Upvote, downvote and score
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            //Like, Downvote
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .width(70.dp)
                                    .height(20.dp)
                                    .background(Color.LightGray)
                                    .shimmerLoadingAnimation()
                            )

                            //Rating
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .width(70.dp)
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

}