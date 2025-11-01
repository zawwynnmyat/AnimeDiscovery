package com.zawmyat.anime_discovery.presentation.staffs.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zawmyat.anime_discovery.CharacterDetailsQuery
import com.zawmyat.anime_discovery.StaffDetailQuery
import com.zawmyat.anime_discovery.presentation.characters.component.MediaGridCell
import com.zawmyat.anime_discovery.presentation.navigation.DetailNavItems

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StaffMediaList(
    medias: StaffDetailQuery.StaffMedia?,
    navController: NavHostController
) {

    medias?.let {
            series ->
        LazyRow(
            modifier = Modifier.padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {

            items(series.nodes?.size ?: 0) {
                    index ->
                MediaGridCell(
                    title = series.nodes?.get(index)?.title?.userPreferred ?: "",
                    coverPhoto = series.nodes?.get(index)?.coverImage?.large ?: "",
                    format = series.nodes?.get(index)?.format?.name ?: "",
                    //role = series.nodes?.get(index)?.characterRole?.name ?: "N/A",
                    role = "N/A",
                    id = series.nodes?.get(index)?.id ?: 0,
                    onMediaClick = {
                            mediaId ->
                        navController.navigate("${DetailNavItems.AnimeDetailPage.route}/id=${mediaId}")
                    }
                )
            }

        }

    }


}