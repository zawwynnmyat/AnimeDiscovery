package com.zawmyat.anime_discovery.presentation.details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zawmyat.anime_discovery.presentation.component.getGenreColor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenreDetails(
    genres: List<String>,
    limit: Int,
) {

    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
    ) {
        genres.take(limit).forEach {
            Box(
                modifier = Modifier.padding(end = 12.dp, bottom = 5.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(color = getGenreColor(it))
                ) {
                    Text(
                        text = it,
                        modifier = Modifier
                            .padding(horizontal = 4.dp),
                        color = Color.White,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }

}