package com.zawmyat.anime_discovery.presentation.manga

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.zawmyat.anime_discovery.R
import com.zawmyat.anime_discovery.presentation.anime.component.AnimeMenuItem
import com.zawmyat.anime_discovery.presentation.anime.component.AnimeMenuOperation
import com.zawmyat.anime_discovery.presentation.manga.components.MangaAppBar
import com.zawmyat.anime_discovery.presentation.manga.components.MangaMenu
import com.zawmyat.anime_discovery.presentation.manga.components.MangaMenuItem
import com.zawmyat.anime_discovery.presentation.manga.components.MangaMenuOperation
import com.zawmyat.anime_discovery.presentation.navigation.DetailNavItems

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Manga(
    navController : NavHostController,
    accessToken: String
) {

    val mangaMenus = listOf(
        MangaMenu(
            title = "Highest Rated",
            icon = R.drawable.numbers_24,
            operation = MangaMenuOperation.TOP100
        ),
        MangaMenu(
            title = "Top Popular",
            icon = R.drawable.trending_up_24,
            operation = MangaMenuOperation.TOP_POPULAR
        ),
        MangaMenu(
            title = "Upcoming",
            icon = R.drawable.upcoming_24,
            operation = MangaMenuOperation.UPCOMING
        ),
        MangaMenu(
            title = "Publishing",
            icon = R.drawable.book,
            operation = MangaMenuOperation.PUBLISHING
        )
    )

    Scaffold(
        topBar = {
            MangaAppBar(
                onSearchClick = {
                    navController.navigate(DetailNavItems.SearchMediaPage.route + "/mediaType=MANGA")
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp)
            ) {

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    mangaMenus.forEach {
                        MangaMenuItem(menu = it) {
                            when(it) {
                                MangaMenuOperation.TOP100 -> navController.navigate("${DetailNavItems.SortedMediaPage.route}/sort=${"Top 100"}/mediaType=${"MANGA"}")
                                MangaMenuOperation.TOP_POPULAR -> navController.navigate("${DetailNavItems.SortedMediaPage.route}/sort=${"Top Popular"}/mediaType=${"MANGA"}")
                                MangaMenuOperation.UPCOMING -> navController.navigate("${DetailNavItems.StatusFilteredMediaPage.route}/status=${"Upcoming"}/mediaType=${"MANGA"}")
                                MangaMenuOperation.PUBLISHING -> navController.navigate("${DetailNavItems.StatusFilteredMediaPage.route}/status=${"Publishing"}/mediaType=${"MANGA"}")
                            }
                        }
                    }
                }


            }

        }
    }
}

