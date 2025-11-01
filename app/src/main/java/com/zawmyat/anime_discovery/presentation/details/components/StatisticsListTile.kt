package com.zawmyat.anime_discovery.presentation.details.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zawmyat.anime_discovery.data.utils.format

@Composable
fun StatisticsListTile(
    average: Int,
    mean: Int,
    popularity: Int,
    favorites: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 25.dp)
    ) {
        StatisticsCell(
            title = "Average",
            content = format(average) + "%",
            modifier = Modifier.weight(1f)
        )

        StatisticsCell(
            title = "Mean",
            content = format(mean) + "%",
            modifier = Modifier.weight(1f)
        )

        StatisticsCell(
            title = "Popularity",
            content = format(popularity),
            modifier = Modifier.weight(1f)
        )

        StatisticsCell(
            title = "Favorites",
            content = format(favorites),
            modifier = Modifier.weight(1f)
        )

    }
}