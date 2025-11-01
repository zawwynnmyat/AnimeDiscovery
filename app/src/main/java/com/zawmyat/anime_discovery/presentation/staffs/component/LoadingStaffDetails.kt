package com.zawmyat.anime_discovery.presentation.staffs.component

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zawmyat.anime_discovery.presentation.component.shimmerLoadingAnimation
import com.zawmyat.anime_discovery.presentation.details.components.AppBar


@Composable
fun LoadingStaffDetails(
    navController : NavHostController
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBar(title = "") {
                navController.popBackStack()
            }
        }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {

                Row {

                    //Avatar
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .width(120.dp)
                            .height(175.dp)
                            .background(color = Color.LightGray)
                            .shimmerLoadingAnimation()
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    //Name, Native Name, Favorites and Gender
                    Column {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .fillMaxWidth()
                                .height(20.dp)
                                .background(color = Color.LightGray)
                                .shimmerLoadingAnimation()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .width(150.dp)
                                .height(20.dp)
                                .background(color = Color.LightGray)
                                .shimmerLoadingAnimation()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .width(90.dp)
                                .height(20.dp)
                                .background(color = Color.LightGray)
                                .shimmerLoadingAnimation()
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .width(90.dp)
                                .height(20.dp)
                                .background(color = Color.LightGray)
                                .shimmerLoadingAnimation()
                        )

                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                //Blood Type
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(4.dp))
                            .height(20.dp)
                            .background(color = Color.LightGray)
                            .shimmerLoadingAnimation()
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(4.dp))
                            .height(20.dp)
                            .background(color = Color.LightGray)
                            .weight(1f)
                            .shimmerLoadingAnimation()
                    )

                }

                Spacer(modifier = Modifier.height(10.dp))

                //Age
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .height(20.dp)
                            .background(color = Color.LightGray)
                            .weight(1f)
                            .shimmerLoadingAnimation()
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .height(20.dp)
                            .background(color = Color.LightGray)
                            .weight(1f)
                            .shimmerLoadingAnimation()
                    )

                }

                Spacer(modifier = Modifier.height(30.dp))

                //Description
                Column {
                    for(i in 1..5) {
                        Box(
                            modifier = Modifier.padding(bottom = 10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .fillMaxWidth()
                                    .height(20.dp)
                                    .background(color = Color.LightGray)
                                    .shimmerLoadingAnimation()
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                //Characters Title
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .width(70.dp)
                            .height(20.dp)
                            .background(color = Color.LightGray)
                            .shimmerLoadingAnimation()
                    )


                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .width(70.dp)
                            .height(20.dp)
                            .background(color = Color.LightGray)
                            .shimmerLoadingAnimation()
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                //Characters
                LazyRow(
                    userScrollEnabled = false,
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

                Spacer(modifier = Modifier.height(15.dp))

                //Media Title
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .width(70.dp)
                            .height(20.dp)
                            .background(color = Color.LightGray)
                            .shimmerLoadingAnimation()
                    )


                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .width(70.dp)
                            .height(20.dp)
                            .background(color = Color.LightGray)
                            .shimmerLoadingAnimation()
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                //Medias
                LazyRow(userScrollEnabled = false) {
                    items(10) {
                        Column(
                            modifier = Modifier
                                .width(110.dp)
                                .padding(end = 12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .width(110.dp)
                                    .height(165.dp)
                                    .background(color = Color.LightGray)
                                    .shimmerLoadingAnimation()
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .width(65.dp)
                                    .height(20.dp)
                                    .background(color = Color.LightGray)
                                    .shimmerLoadingAnimation()
                            )
                        }
                    }
                }




            }
        }
    }

}