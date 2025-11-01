package com.zawmyat.anime_discovery.presentation.anime

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.zawmyat.anime_discovery.R
import com.zawmyat.anime_discovery.data.auth.Utils
import com.zawmyat.anime_discovery.presentation.anime.component.AnimeAppBar
import com.zawmyat.anime_discovery.presentation.anime.component.AnimeMenu
import com.zawmyat.anime_discovery.presentation.anime.component.AnimeMenuItem
import com.zawmyat.anime_discovery.presentation.anime.component.AnimeMenuOperation
import com.zawmyat.anime_discovery.presentation.navigation.DetailNavItems

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Anime(
    navController: NavHostController,
    accessToken: String
) {

    val animeFilterMenus = listOf(
        AnimeMenu(
            title = "Highest Rated",
            icon = R.drawable.numbers_24,
            operation = AnimeMenuOperation.TOP100
        ),
        AnimeMenu(
            title = "Top Popular",
            icon = R.drawable.trending_up_24,
            operation = AnimeMenuOperation.TOP_POPULAR
        ),
        AnimeMenu(
            title = "Upcoming",
            icon = R.drawable.upcoming_24,
            operation = AnimeMenuOperation.UPCOMING
        ),
        AnimeMenu(
            title = "Airing",
            icon = R.drawable.connected_tv_24,
            operation = AnimeMenuOperation.AIRING
        ),
    )

    val seasonalMenus = listOf(
        AnimeMenu(
            title = "Spring",
            icon = R.drawable.ic_grass_24,
            operation = AnimeMenuOperation.SPRING
        ),
        AnimeMenu(
            title = "Summer",
            icon = R.drawable.sunny,
            operation = AnimeMenuOperation.SUMMER
        ),
        AnimeMenu(
            title = "Fall",
            icon = R.drawable.ic_cloudy_snowing_24,
            operation = AnimeMenuOperation.FALL
        ),
        AnimeMenu(
            title = "Winter",
            icon = R.drawable.snowing_24,
            operation = AnimeMenuOperation.WINTER
        ),
    )

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            AnimeAppBar(
                onSearchClick = {
                    navController.navigate(DetailNavItems.SearchMediaPage.route + "/mediaType=ANIME")
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
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
                    animeFilterMenus.forEach {
                        AnimeMenuItem(menu = it) {
                            when(it) {
                                AnimeMenuOperation.TOP100 -> navController.navigate("${DetailNavItems.SortedMediaPage.route}/sort=${"Top 100"}/mediaType=${"ANIME"}")
                                AnimeMenuOperation.TOP_POPULAR -> navController.navigate("${DetailNavItems.SortedMediaPage.route}/sort=${"Top Popular"}/mediaType=${"ANIME"}")
                                AnimeMenuOperation.UPCOMING -> navController.navigate("${DetailNavItems.StatusFilteredMediaPage.route}/status=${"Upcoming"}/mediaType=${"ANIME"}")
                                AnimeMenuOperation.AIRING -> navController.navigate("${DetailNavItems.StatusFilteredMediaPage.route}/status=${"Airing"}/mediaType=${"ANIME"}")
                                else -> {
                                    navController.navigate("${DetailNavItems.StatusFilteredMediaPage.route}/status=${"Airing"}/mediaType=${"ANIME"}")
                                }
                            }
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.height(15.dp)
                )

                Text(
                    text = "Seasonal",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    seasonalMenus.forEach {
                        AnimeMenuItem(menu = it) {
                            when(it) {
                                AnimeMenuOperation.SPRING -> navController.navigate("${DetailNavItems.SeasonalPage.route}/season=${"SPRING"}/mediaType=${"ANIME"}")
                                AnimeMenuOperation.SUMMER -> navController.navigate("${DetailNavItems.SeasonalPage.route}/season=${"SUMMER"}/mediaType=${"ANIME"}")
                                AnimeMenuOperation.FALL -> navController.navigate("${DetailNavItems.SeasonalPage.route}/season=${"FALL"}/mediaType=${"ANIME"}")
                                AnimeMenuOperation.WINTER -> navController.navigate("${DetailNavItems.SeasonalPage.route}/season=${"WINTER"}/mediaType=${"ANIME"}")
                                else -> {
                                    navController.navigate("${DetailNavItems.SeasonalPage.route}/season=${"SPRING"}/mediaType=${"ANIME"}")
                                }
                            }
                        }
                    }
                }


//                Spacer(
//                    modifier = Modifier.height(15.dp)
//                )
//
//                Text(
//                    text = "Anime List",
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold
//                )
//
//                Spacer(
//                    modifier = Modifier.height(10.dp)
//                )
//
//                if(accessToken.isEmpty()) {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(250.dp),
//                        contentAlignment = Alignment.Center,
//                    ) {
//
//                        Column(
//                            horizontalAlignment = Alignment.CenterHorizontally,
//                            verticalArrangement = Arrangement.Center
//                        ) {
//
//                            Button(
//                                onClick = {
//                                    Utils().login(context = context)
//                                }
//                            ) {
//                                Text(text = "Login with AniList")
//                            }
//
//                            Spacer(modifier = Modifier.height(15.dp))
//
//                            Text(
//                                text = "You need to login in with AniList to save and manage Anime lists.",
//                                fontSize = 13.sp,
//                                textAlign = TextAlign.Center,
//                                modifier = Modifier.padding(horizontal = 13.dp)
//                            )
//                        }
//
//                    }
//                } else {
//                    Text(text = "anime list shows here")
//                }



            }
        }
    }
}

