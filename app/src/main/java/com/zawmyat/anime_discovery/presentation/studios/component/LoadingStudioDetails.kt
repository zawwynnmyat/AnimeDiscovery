package com.zawmyat.anime_discovery.presentation.studios.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.zawmyat.anime_discovery.presentation.component.LoadingMediaGrid
import com.zawmyat.anime_discovery.presentation.component.shimmerLoadingAnimation
import com.zawmyat.anime_discovery.presentation.details.components.AppBar

@Composable
fun LoadingStudioDetails(
    navController: NavHostController
) {

    Scaffold(
        topBar = {
            AppBar(title = "") {
                navController.popBackStack()
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.End
        ) {

            Box(modifier = Modifier.padding(end = 10.dp)) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .height(20.dp)
                        .width(100.dp)
                        .background(color = Color.LightGray)
                        .shimmerLoadingAnimation()
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            LoadingMediaGrid()

        }
    }

}