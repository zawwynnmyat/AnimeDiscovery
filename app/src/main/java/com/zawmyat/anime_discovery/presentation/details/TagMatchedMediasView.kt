package com.zawmyat.anime_discovery.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.zawmyat.anime_discovery.presentation.component.AnimeGridCell
import com.zawmyat.anime_discovery.presentation.component.AnimeListTile
import com.zawmyat.anime_discovery.presentation.component.LoadingMediaList
import com.zawmyat.anime_discovery.presentation.navigation.DetailNavItems
import com.zawmyat.anime_discovery.presentation.trending.component.TrendingAppBar
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun TagMatchedMediaView(
    tag: String,
    navController : NavHostController
) {

    val tagMatchedViewModel : TagMatchedViewModel = koinViewModel<TagMatchedViewModel>(
        parameters = {
            parametersOf(tag)
        }
    )

    val tagMatchedMedias = tagMatchedViewModel.tagMatchedMedias.collectAsLazyPagingItems()

    var isList by rememberSaveable {
        mutableStateOf(true)
    }

    Scaffold(
        topBar = {
            TrendingAppBar(
                title = tag,
                isList = isList,
                onNavBackClick = {
                    navController.popBackStack()
                },
                onStyleChange = {
                    isList = !isList
                }
            )
        },
        bottomBar = {
            Box(modifier = Modifier.height(0.dp))
        }
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            if(tagMatchedMedias.loadState.refresh == LoadState.Loading) {

                LoadingMediaList()

            } else {

                if(isList) {

                    LazyColumn(modifier = Modifier.padding(horizontal = 12.dp)) {
                        items(tagMatchedMedias.itemCount) {
                                index ->
                            tagMatchedMedias[index]?.let {
                                AnimeListTile(
                                    id = it.id ?: 0,
                                    title = it.title?.userPreferred ?: "",
                                    favorite = it.favourites ?: 0,
                                    coverPhoto = it.coverImage?.large ?: "",
                                    genres = it.genres?.filterNotNull() ?: emptyList(),
                                    startYear = it.startDate?.year ?: 0,
                                    averageScore = it.averageScore ?: 0,
                                    format = it.format?.name ?: "",
                                    onClick = {
                                            mediaId ->
                                        navController.navigate("${DetailNavItems.AnimeDetailPage.route}/id=${mediaId}")
                                    }
                                )
                            }
                        }

                        tagMatchedMedias.apply {
                            when {
                                loadState.refresh is LoadState.Loading -> {
                                    item {
                                        Column(
                                            modifier = Modifier
                                                .fillParentMaxSize(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center,
                                        ) {
                                            Text(
                                                modifier = Modifier
                                                    .padding(8.dp),
                                                text = "Fetching Data",
                                            )

                                            Spacer(modifier = Modifier.height(10.dp))

                                            CircularProgressIndicator()
                                        }
                                    }
                                }

                                loadState.refresh is LoadState.Error -> {

                                }

                                loadState.append is LoadState.Loading -> {
                                    item {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center,
                                        ) {

                                            CircularProgressIndicator()
                                        }
                                    }
                                }

                                loadState.append is LoadState.Error -> {

                                }
                            }
                        }
                    }

                } else {

                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(100.dp),
                        modifier = Modifier.padding(horizontal = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        items(tagMatchedMedias.itemCount) {
                                index ->
                            tagMatchedMedias[index]?.let {

                                AnimeGridCell(
                                    id = it.id ?: 0,
                                    format = it.format?.name ?: "",
                                    title = it.title?.userPreferred ?: "",
                                    coverPhoto = it.coverImage?.large ?: "",
                                    year = it.startDate?.year ?: 0,
                                    averageScore = it.averageScore ?: 0,
                                    onClick = {
                                            mediaId ->
                                        navController.navigate("${DetailNavItems.AnimeDetailPage.route}/id=${mediaId}")
                                    }
                                )

                            }
                        }

                        tagMatchedMedias.apply {
                            when {
                                loadState.refresh is LoadState.Loading -> {
                                    item {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center,
                                        ) {
                                            Text(
                                                modifier = Modifier
                                                    .padding(8.dp),
                                                text = "Fetching Data",
                                            )

                                            Spacer(modifier = Modifier.height(10.dp))

                                            CircularProgressIndicator()
                                        }
                                    }
                                }

                                loadState.refresh is LoadState.Error -> {

                                }

                                loadState.append is LoadState.Loading -> {
                                    item {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center,
                                        ) {

                                            CircularProgressIndicator()
                                        }
                                    }
                                }

                                loadState.append is LoadState.Error -> {

                                }
                            }
                        }

                    }
                }

            }


        }

    }

}