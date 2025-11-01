package com.zawmyat.anime_discovery.presentation.characters

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zawmyat.anime_discovery.R
import com.zawmyat.anime_discovery.data.utils.format
import com.zawmyat.anime_discovery.presentation.base.MainActivity
import com.zawmyat.anime_discovery.presentation.characters.component.CharacterListTile
import com.zawmyat.anime_discovery.presentation.characters.component.CharactersAppBar
import com.zawmyat.anime_discovery.presentation.characters.component.LoadingCharacters
import com.zawmyat.anime_discovery.presentation.characters.component.LoadingCharactersListTile
import com.zawmyat.anime_discovery.presentation.home.HomeViewModel
import com.zawmyat.anime_discovery.presentation.navigation.BottomNavItems
import com.zawmyat.anime_discovery.presentation.navigation.DetailNavItems
import com.zawmyat.anime_discovery.presentation.reviews.component.ReviewListTile
import com.zawmyat.anime_discovery.presentation.reviews.component.ReviewsAppBar
import com.zawmyat.anime_discovery.presentation.shortcut.ShortcutName
import com.zawmyat.anime_discovery.presentation.shortcut.ShortcutViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.definition.indexKey

@Composable
fun Characters(
    navController: NavHostController,
    shortcutViewModel: ShortcutViewModel = viewModel(LocalContext.current as MainActivity)
    ) {

    val allCharactersViewModel : AllCharactersViewModel = koinViewModel<AllCharactersViewModel>()
    val allCharacters = allCharactersViewModel.allCharacters.collectAsLazyPagingItems()

    Scaffold(
        modifier = Modifier,
        topBar = {
            CharactersAppBar(
                onBack = {
                    if(shortcutViewModel.shortcutName == ShortcutName.CHARACTER) {
                        shortcutViewModel.onShortcutClick(null)
                        navController.popBackStack()
                    } else {
                        navController.popBackStack()
                    }
                },
                onSearchClick = {
                    navController.navigate(DetailNavItems.SearchCharacters.route)
                }
            )
        }
    ) {

        BackHandler {
            if(shortcutViewModel.shortcutName == ShortcutName.CHARACTER) {
                shortcutViewModel.onShortcutClick(null)
                navController.popBackStack()
            } else {
                navController.popBackStack()
            }
        }

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
            ) {
                items(count = allCharacters.itemCount) {
                        index ->
                    CharacterListTile(
                        id = allCharacters[index]?.id ?: 0,
                        name = allCharacters[index]?.name?.full ?: "",
                        photo = allCharacters[index]?.image?.large ?: "",
                        favorites = allCharacters[index]?.favourites ?: 0,
                        gender = allCharacters[index]?.gender ?: "Unknown",
                        age = allCharacters[index]?.age ?: "",
                        count = index,
                        onClick = {
                            clickedId ->

                            navController.navigate("${DetailNavItems.CharacterDetailsPage.route}/id=${clickedId}")
                        }
                    )
                }


                allCharacters.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                LoadingCharacters()
                            }
                        }

                        loadState.refresh is LoadState.Error -> {

                        }

                        loadState.append is LoadState.Loading -> {
                            item {
                                LoadingCharactersListTile()
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
