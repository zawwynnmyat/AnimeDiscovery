package com.zawmyat.anime_discovery.presentation.search

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.zawmyat.anime_discovery.R
import com.zawmyat.anime_discovery.presentation.component.AnimeListTile
import com.zawmyat.anime_discovery.presentation.navigation.DetailNavItems
import com.zawmyat.anime_discovery.presentation.search.components.SearchBarCustom
import com.zawmyat.anime_discovery.presentation.search.components.SearchPrompt
import com.zawmyat.anime_discovery.type.MediaType
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SearchMediaPage(
    mediaType : MediaType,
    navController: NavHostController,
) {

    val searchMediaViewModel: SearchMediaViewModel = koinViewModel<SearchMediaViewModel>(
        parameters = {
            parametersOf(mediaType)
        }
    )

    val searchQuery by searchMediaViewModel.queryFlow.collectAsState()
    val searchResults = searchMediaViewModel.searchResults.collectAsLazyPagingItems()
    val isSearching by searchMediaViewModel.isSearching.collectAsStateWithLifecycle()

    Scaffold(

    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchBarCustom(
                    query = searchQuery,
                    prompt = "Search ${mediaType.toString().lowercase()}...",
                    onQueryChange = searchMediaViewModel::onSearchQueryChange,
                    onFinishClicked = {
                        navController.popBackStack()
                    },
                    onTextClear = {
                        searchMediaViewModel.queryFlow.value = ""
                    }
                )
            }

            if(isSearching) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            if(searchQuery.isEmpty()) {
                SearchPrompt(searchType = mediaType.toString())
            } else {
                if(searchResults.itemCount == 0) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_hourglass_empty_24),
                            contentDescription = null
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(text = "There is no searched media.")
                    }
                } else {

                    LazyColumn(
                        modifier = Modifier.padding(all = 10.dp)
                    ) {

                        items(count = searchResults.itemCount) {
                                index ->
                            searchResults[index]?.let {
                                    result ->
                                AnimeListTile(
                                    id = result.id,
                                    title = result.title?.english ?: "N/A",
                                    favorite = result.favourites ?: 0,
                                    coverPhoto = result.coverImage?.large ?: "",
                                    genres = result.genres?.filterNotNull() ?: emptyList(),
                                    startYear =result.startDate?.year ?: 0 ,
                                    averageScore = result.averageScore ?: 0,
                                    format = result.format?.name ?: "",
                                    onClick = {
                                            clickedId ->
                                        navController.navigate("${DetailNavItems.AnimeDetailPage.route}/id=${clickedId}")
                                    }
                                )


                            }
                        }

                        searchResults.apply {
                            when {

                                loadState.refresh is LoadState.Error -> {
                                    searchMediaViewModel.isSearching.value = false
                                }

                                loadState.refresh is LoadState.Loading -> {
                                    searchMediaViewModel.isSearching.value = true
                                }

                                loadState.refresh is LoadState.NotLoading -> {
                                    searchMediaViewModel.isSearching.value = false
                                }

                                loadState.append is LoadState.Loading -> {
                                    searchMediaViewModel.isSearching.value = true
                                }

                                loadState.append is LoadState.Error -> {
                                    searchMediaViewModel.isSearching.value = false
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}