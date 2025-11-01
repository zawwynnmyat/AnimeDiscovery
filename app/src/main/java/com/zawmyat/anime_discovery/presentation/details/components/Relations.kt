package com.zawmyat.anime_discovery.presentation.details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zawmyat.anime_discovery.MediaQuery
import com.zawmyat.anime_discovery.presentation.characters.component.MediaGridCell

@Composable
fun Relations(
    relations: MediaQuery.Relations?,
    onMediaClick : (id : Int) -> Unit
) {
    Column {
        Text(
            text = "Relations",
            modifier = Modifier.padding(vertical = 15.dp)
        )

        LazyRow {
            items(relations?.edges?.size ?: 0) {
                    index ->
                Box(modifier = Modifier.padding(end = 12.dp)) {
                    MediaGridCell(
                        title = relations?.edges?.get(index)?.node?.title?.userPreferred ?: "N/A",
                        coverPhoto = relations?.edges?.get(index)?.node?.coverImage?.large ?: "N/A",
                        format = relations?.edges?.get(index)?.node?.format?.name ?: "N/A",
                        role = relations?.edges?.get(index)?.relationType?.name ?: "N/A",
                        id = relations?.edges?.get(index)?.node?.id ?: 0
                    ) {
                            mediaId ->
                        onMediaClick(mediaId)
                    }
                }
            }
        }
    }
}