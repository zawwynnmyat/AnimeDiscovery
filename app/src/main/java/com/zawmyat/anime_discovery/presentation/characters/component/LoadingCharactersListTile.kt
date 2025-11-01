package com.zawmyat.anime_discovery.presentation.characters.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zawmyat.anime_discovery.presentation.component.shimmerLoadingAnimation

@Composable
fun LoadingCharactersListTile() {
    Box(modifier = Modifier.padding(bottom = 12.dp)) {
        Row {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .width(110.dp)
                    .height(165.dp)
                    .background(color = Color.LightGray)
                    .shimmerLoadingAnimation()
            )

            Column(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .fillMaxWidth()
                        .height(20.dp)
                        .background(color = Color.LightGray)
                        .shimmerLoadingAnimation()
                )

                Spacer(modifier = Modifier.height(5.dp))

                //Favorites
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .width(100.dp)
                        .height(20.dp)
                        .background(color = Color.LightGray)
                        .shimmerLoadingAnimation()
                )

                Spacer(modifier = Modifier.height(5.dp))

                //Gender
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .width(100.dp)
                        .height(20.dp)
                        .background(color = Color.LightGray)
                        .shimmerLoadingAnimation()
                )

                Spacer(modifier = Modifier.height(5.dp))

                //Age
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .width(100.dp)
                        .height(20.dp)
                        .background(color = Color.LightGray)
                        .shimmerLoadingAnimation()
                )

            }
        }
    }
}