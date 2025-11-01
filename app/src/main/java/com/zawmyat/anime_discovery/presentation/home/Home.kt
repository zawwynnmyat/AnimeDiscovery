package com.zawmyat.anime_discovery.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zawmyat.anime_discovery.R
import com.zawmyat.anime_discovery.TrendingAnimeHomeQuery
import com.zawmyat.anime_discovery.TrendingMangaHomeQuery
import com.zawmyat.anime_discovery.data.utils.format
import com.zawmyat.anime_discovery.presentation.base.MainActivity
import com.zawmyat.anime_discovery.presentation.component.AnimeCard
import com.zawmyat.anime_discovery.presentation.component.MangaCard
import com.zawmyat.anime_discovery.presentation.component.Title
import com.zawmyat.anime_discovery.presentation.component.getGenreColor
import com.zawmyat.anime_discovery.presentation.home.component.HomeAppBar
import com.zawmyat.anime_discovery.presentation.home.component.HomeExploreItem
import com.zawmyat.anime_discovery.presentation.home.component.HomeExploreMenuCell
import com.zawmyat.anime_discovery.presentation.home.component.HomeExploreOperation
import com.zawmyat.anime_discovery.presentation.home.component.LoadingHomeMedia
import com.zawmyat.anime_discovery.presentation.navigation.DetailNavItems
import com.zawmyat.anime_discovery.presentation.navigation.Graph
import com.zawmyat.anime_discovery.presentation.shortcut.ShortcutName
import com.zawmyat.anime_discovery.presentation.shortcut.ShortcutViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    navController: NavController,
    homeViewModel: HomeViewModel = koinViewModel<HomeViewModel>(),
    shortcutViewModel: ShortcutViewModel = viewModel(LocalContext.current as MainActivity)
) {

    val trendingAnimeHome = homeViewModel.trendingAnimeHome.collectAsState()
    val isLoadingTrendingAnimeHome = homeViewModel.loadingTrendingAnimeHome.collectAsState()

    val trendingMangaHome = homeViewModel.trendingMangaHome.collectAsState()
    val isLoadingTrendingMangaHome = homeViewModel.loadingTrendingMangaHome.collectAsState()

    val scrollState = rememberScrollState()

    val homeExploreMenus = listOf(
        HomeExploreItem(
            icon = R.drawable.face_24,
            title = "Characters",
            operation = HomeExploreOperation.CHARACTERS
        ),
        HomeExploreItem(
            icon = R.drawable.broadcast_on_home_24,
            title = "Studios",
            operation = HomeExploreOperation.STUDIOS
        ),
        HomeExploreItem(
            icon = R.drawable.face_4_24,
            title = "Staffs",
            operation = HomeExploreOperation.STAFFS
        ),
        HomeExploreItem(
            icon = R.drawable.rate_review_24,
            title = "Reviews",
            operation = HomeExploreOperation.REVIEWS
        ),
    )

    Scaffold(
        topBar = {
           HomeAppBar(
               onSearchClick = {
                   navController.navigate(DetailNavItems.SearchMediaPage.route + "/mediaType=ANIME")
               }
           )
        },
        bottomBar = {
            Box(modifier = Modifier.size(0.dp))
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(state = scrollState)
        ) {

            Spacer(modifier = Modifier.height(25.dp))

            when(shortcutViewModel.shortcutName) {
                ShortcutName.CHARACTER -> navController.navigate(DetailNavItems.CharactersPage.route)
                ShortcutName.STAFFS -> navController.navigate(DetailNavItems.StaffsPage.route)
                ShortcutName.STUDIOS -> navController.navigate(DetailNavItems.StudiosPage.route)
                ShortcutName.REVIEWS -> navController.navigate(DetailNavItems.ReviewsPage.route)
                null -> Unit
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                homeExploreMenus.forEach {
                    HomeExploreMenuCell(
                        homeExploreItem = it
                    ) {
                        operation ->
                        when(operation) {
                            HomeExploreOperation.CHARACTERS -> navController.navigate(DetailNavItems.CharactersPage.route)
                            HomeExploreOperation.STAFFS -> navController.navigate(DetailNavItems.StaffsPage.route)
                            HomeExploreOperation.STUDIOS -> navController.navigate(DetailNavItems.StudiosPage.route)
                            HomeExploreOperation.REVIEWS -> navController.navigate(DetailNavItems.ReviewsPage.route)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Title(
                title = "Trending Anime",
                onSeeAllClick = {
                    navController.navigate(DetailNavItems.AllTrendingAnimePage.route)
                }
            )

            Spacer(modifier = Modifier.height(5.dp))

            if(isLoadingTrendingAnimeHome.value) {
                LoadingHomeMedia()

            } else {

                trendingAnimeHome.value?.let {
                    trendingAnimes ->

                    LazyRow(
                        modifier = Modifier
                            .padding(horizontal = 12.dp),
                    ) {

                        items(trendingAnimes) {

                            AnimeCard(
                                title = it?.title?.english ?: "",
                                studios = it?.studios?.edges?.filterNotNull().orEmpty(),
                                favorite = it?.favourites?.let {
                                    it
                                } ?: 0,
                                coverPhoto = it?.coverImage?.large ?: "",
                                bannerImage = it?.bannerImage ?: "",
                                description = it?.description ?: "",
                                genres = it?.genres?.filterNotNull().orEmpty(),
                                startYear = it?.startDate?.year ?: 0,
                                averageScore = it?.averageScore ?: 0,
                                onClick = {
                                    navController.navigate(DetailNavItems.AnimeDetailPage.route + "/id=${it?.id ?: 0}")
                                }
                            )
                        }

                    }
                } ?: Text(text = "Connection issue")
                
            }

            Spacer(modifier = Modifier.height(10.dp))

            Title(
                title = "Trending Manga",
                onSeeAllClick = {
                    navController.navigate(DetailNavItems.AllTrendingMangaPage.route)
                }
            )


            Spacer(modifier = Modifier.height(5.dp))

            if(isLoadingTrendingMangaHome.value) {
                LoadingHomeMedia()
            } else {

                trendingMangaHome.value?.let {
                        trendingMangas ->

                    LazyRow(
                        modifier = Modifier
                            .padding(horizontal = 12.dp),
                    ) {

                        items(trendingMangas) {

                            MangaCard(
                                title = it?.title?.english ?: "",
                                staffs = it?.staff?.edges?.filterNotNull().orEmpty(),
                                favorite = it?.favourites?.let {
                                    it
                                } ?: 0,
                                coverPhoto = it?.coverImage?.large ?: "",
                                bannerImage = it?.bannerImage ?: "",
                                description = it?.description ?: "",
                                genres = it?.genres?.filterNotNull().orEmpty(),
                                startYear = it?.startDate?.year ?: 0,
                                averageScore = it?.averageScore ?: 0,
                                onClick = {
                                    navController.navigate("${DetailNavItems.AnimeDetailPage.route}/id=${it?.id ?: 0}")
                                }
                            )
                        }

                    }
                } ?: Text(text = "Connection issue")

            }

            Spacer(modifier = Modifier.height(10.dp))

        }
    }
    
}



