package com.zawmyat.anime_discovery.presentation.search

import androidx.compose.foundation.layout.Arrangement
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
import com.zawmyat.anime_discovery.presentation.characters.component.CharacterListTile
import com.zawmyat.anime_discovery.presentation.component.AnimeListTile
import com.zawmyat.anime_discovery.presentation.navigation.DetailNavItems
import com.zawmyat.anime_discovery.presentation.search.components.SearchBarCustom
import com.zawmyat.anime_discovery.presentation.search.components.SearchPrompt
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SearchCharactersPage(
    navController : NavHostController,
    searchCharactersViewModel: SearchCharactersViewModel = koinViewModel<SearchCharactersViewModel>()
) {

    val searchQuery by searchCharactersViewModel.queryFlow.collectAsState()
    val searchResults = searchCharactersViewModel.searchResults.collectAsLazyPagingItems()
    val isSearching by searchCharactersViewModel.isSearching.collectAsStateWithLifecycle()

    Scaffold() {
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
                    prompt = "Search characters...",
                    onQueryChange = searchCharactersViewModel::onSearchQueryChange,
                    onFinishClicked = {
                        navController.popBackStack()
                    },
                    onTextClear = {
                        searchCharactersViewModel.queryFlow.value = ""
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
                SearchPrompt(searchType = "Characters")
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

                        Text(text = "There is no searched characters.")
                    }
                } else {

                    LazyColumn(
                        modifier = Modifier.padding(all = 10.dp)
                    ) {

                        items(count = searchResults.itemCount) {
                                index ->
                            searchResults[index]?.let {
                                    result ->
                                CharacterListTile(
                                    id = result.id,
                                    name = result.name?.full ?: "",
                                    photo = result.image?.large ?: "",
                                    favorites = result.favourites ?: 0,
                                    gender = result.gender ?: "",
                                    age = result.age ?: "",
                                    count = index
                                ) {
                                    clickedId ->
                                    navController.navigate("${DetailNavItems.CharacterDetailsPage.route}/id=${clickedId}")
                                }


                            }
                        }

                        searchResults.apply {
                            when {

                                loadState.refresh is LoadState.Error -> {
                                    searchCharactersViewModel.isSearching.value = false
                                }

                                loadState.refresh is LoadState.Loading -> {
                                    searchCharactersViewModel.isSearching.value = true
                                }

                                loadState.refresh is LoadState.NotLoading -> {
                                    searchCharactersViewModel.isSearching.value = false
                                }

                                loadState.append is LoadState.Loading -> {
                                    searchCharactersViewModel.isSearching.value = true
                                }

                                loadState.append is LoadState.Error -> {
                                    searchCharactersViewModel.isSearching.value = false
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}