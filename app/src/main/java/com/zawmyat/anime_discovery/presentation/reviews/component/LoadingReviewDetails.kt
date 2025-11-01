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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zawmyat.anime_discovery.R
import com.zawmyat.anime_discovery.data.utils.getDateString
import com.zawmyat.anime_discovery.presentation.component.shimmerLoadingAnimation
import com.zawmyat.anime_discovery.presentation.details.components.AppBar

@Composable
fun LoadingReviewDetails(
    navController : NavController
) {

    Scaffold(
        topBar = {
            AppBar(title = "") {
                navController.popBackStack()
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 12.dp, end = 12.dp, top = 12.dp)
            ) {

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(3.dp))
                        .width(80.dp)
                        .height(15.dp)
                        .background(color = Color.LightGray)
                        .shimmerLoadingAnimation()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(3.dp))
                        .fillMaxWidth()
                        .height(20.dp)
                        .background(color = Color.LightGray)
                        .shimmerLoadingAnimation()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(3.dp))
                        .width(200.dp)
                        .height(15.dp)
                        .background(color = Color.LightGray)
                        .shimmerLoadingAnimation()
                )

                Spacer(modifier = Modifier.height(15.dp))

                Row {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .width(50.dp)
                            .height(50.dp)
                            .background(color = Color.LightGray)
                            .shimmerLoadingAnimation()
                    )

                    Spacer(modifier = Modifier.width(8.dp))


                    Column {

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(3.dp))
                                .fillMaxWidth()
                                .height(15.dp)
                                .background(color = Color.LightGray)
                                .shimmerLoadingAnimation()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(3.dp))
                                .width(100.dp)
                                .height(15.dp)
                                .background(color = Color.LightGray)
                                .shimmerLoadingAnimation()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(50.dp))

                Column {
                    for(i in 1..10) {
                        Box(modifier = Modifier.padding(bottom = 10.dp)) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(3.dp))
                                    .fillMaxWidth()
                                    .height(15.dp)
                                    .background(color = Color.LightGray)
                                    .shimmerLoadingAnimation()
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(50.dp))

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .width(100.dp)
                            .height(30.dp)
                            .background(color = Color.LightGray)
                            .shimmerLoadingAnimation()
                    )

                }


            }
        }
    }

}