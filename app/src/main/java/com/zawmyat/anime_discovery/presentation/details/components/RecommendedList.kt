package com.zawmyat.anime_discovery.presentation.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zawmyat.anime_discovery.MediaQuery

@Composable
fun RecommendedList(
    recommendations : MediaQuery.Recommendations?,
    onItemClick : (id: Int) -> Unit
) {
    Column(
        modifier = Modifier.padding(vertical = 20.dp)
    ) {

        Text(
            text = "Recommended",
            modifier = Modifier.padding(vertical = 12.dp)
        )

        LazyRow {
            items(recommendations?.edges?.size ?: 0) {
                    index ->
                RecommendedListTile(
                    id = recommendations?.edges?.get(index)?.node?.mediaRecommendation?.id ?: 0,
                    coverPhoto = recommendations?.edges?.get(index)?.node?.mediaRecommendation?.coverImage?.large ?: "",
                    title = recommendations?.edges?.get(index)?.node?.mediaRecommendation?.title?.english ?: "",
                    averageScore = recommendations?.edges?.get(index)?.node?.mediaRecommendation?.averageScore ?: 0,
                    format = recommendations?.edges?.get(index)?.node?.mediaRecommendation?.format?.name ?: "",
                    popularity = recommendations?.edges?.get(index)?.node?.mediaRecommendation?.popularity ?: 0,
                    startYear = recommendations?.edges?.get(index)?.node?.mediaRecommendation?.startDate?.year ?: 0,
                    favorites = recommendations?.edges?.get(index)?.node?.mediaRecommendation?.favourites ?: 0,
                    genres =  recommendations?.edges?.get(index)?.node?.mediaRecommendation?.genres?.filterNotNull() ?: emptyList<String>(),
                    modifier = Modifier
                        .width(320.dp)
                        .height(165.dp)
                        .padding(end = 12.dp),
                    onRecommendedClicked = {
                            clickedId ->
                        onItemClick(clickedId)
                    }
                )
            }
        }



    }
}