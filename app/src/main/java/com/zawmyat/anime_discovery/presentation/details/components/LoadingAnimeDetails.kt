package com.zawmyat.anime_discovery.presentation.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zawmyat.anime_discovery.presentation.component.shimmerLoadingAnimation

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LoadingAnimeDetails(
    navController : NavController
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBar(title = "") {
                navController.popBackStack()
            }
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {

            Spacer(modifier = Modifier.height(100.dp))

            Row(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .width(115.dp)
                        .height(175.dp)
                        .background(color = Color.LightGray)
                        .shimmerLoadingAnimation()
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .fillMaxWidth()
                            .height(20.dp)
                            .background(color = Color.LightGray)
                            .shimmerLoadingAnimation()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .width(100.dp)
                            .height(20.dp)
                            .background(color = Color.LightGray)
                            .shimmerLoadingAnimation()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .width(100.dp)
                            .height(20.dp)
                            .background(color = Color.LightGray)
                            .shimmerLoadingAnimation()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .width(100.dp)
                            .height(20.dp)
                            .background(color = Color.LightGray)
                            .shimmerLoadingAnimation()
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                for(i in 1..3) {
                    Box(modifier = Modifier.padding(end = 12.dp)) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(3.dp))
                                .width(60.dp)
                                .height(20.dp)
                                .background(color = Color.LightGray)
                                .shimmerLoadingAnimation()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                for(i in 1..6) {

                    Box(modifier = Modifier.padding(bottom = 12.dp)) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(3.dp))
                                .fillMaxWidth()
                                .height(20.dp)
                                .background(color = Color.LightGray)
                                .shimmerLoadingAnimation()
                        )
                    }

                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(3.dp))
                        .width(80.dp)
                        .height(20.dp)
                        .background(color = Color.LightGray)
                        .shimmerLoadingAnimation()
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(3.dp))
                        .width(50.dp)
                        .height(20.dp)
                        .background(color = Color.LightGray)
                        .shimmerLoadingAnimation()
                )
            }

            Spacer(modifier = Modifier.height(10.dp))


            LazyRow(
                userScrollEnabled = false,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {

                items(8) {
                    Column(
                        modifier = Modifier
                            .padding(end = 15.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .width(80.dp)
                                .height(80.dp)
                                .background(color = Color.LightGray)
                                .shimmerLoadingAnimation()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(3.dp))
                                .width(40.dp)
                                .height(15.dp)
                                .background(color = Color.LightGray)
                                .shimmerLoadingAnimation()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                    }
                }
            }

        }
     }
}