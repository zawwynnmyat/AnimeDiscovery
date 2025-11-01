package com.zawmyat.anime_discovery.presentation.studios.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zawmyat.anime_discovery.presentation.component.shimmerLoadingAnimation

@Composable
fun LoadingStudios() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        for(i in 1..10) {

            Box(modifier = Modifier.padding(bottom = 12.dp)) {

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(color = Color.LightGray)
                        .shimmerLoadingAnimation()
                )

            }
        }
    }
}