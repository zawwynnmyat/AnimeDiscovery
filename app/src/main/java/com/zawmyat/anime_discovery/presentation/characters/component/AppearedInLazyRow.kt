package com.zawmyat.anime_discovery.presentation.characters.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zawmyat.anime_discovery.CharacterDetailsQuery
import com.zawmyat.anime_discovery.presentation.navigation.DetailNavItems

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AppeardInLazyRow(
    medias: CharacterDetailsQuery.Media?,
    navController: NavHostController
) {

    medias?.let {

            series ->

        LazyRow(
            modifier = Modifier.padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {

            items(series.edges?.size ?: 0) {
                    index ->
                MediaGridCell(
                    title = series.edges?.get(index)?.node?.title?.userPreferred ?: "",
                    coverPhoto = series.edges?.get(index)?.node?.coverImage?.large ?: "",
                    format = series.edges?.get(index)?.node?.format?.name ?: "",
                    role = series.edges?.get(index)?.characterRole?.name ?: "N/A",
                    id = series.edges?.get(index)?.node?.id ?: 0,
                    onMediaClick = {
                            mediaId ->
                        navController.navigate("${DetailNavItems.AnimeDetailPage.route}/id=${mediaId}")
                    }
                )
            }

        }

    }


}