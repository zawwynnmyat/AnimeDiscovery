package com.zawmyat.anime_discovery.presentation.studios

import android.content.Context.MODE_PRIVATE
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.zawmyat.anime_discovery.R
import com.zawmyat.anime_discovery.data.utils.format
import com.zawmyat.anime_discovery.presentation.base.MainActivity
import com.zawmyat.anime_discovery.presentation.characters.component.CharactersAppBar
import com.zawmyat.anime_discovery.presentation.home.HomeViewModel
import com.zawmyat.anime_discovery.presentation.navigation.DetailNavItems
import com.zawmyat.anime_discovery.presentation.shortcut.ShortcutName
import com.zawmyat.anime_discovery.presentation.shortcut.ShortcutViewModel
import com.zawmyat.anime_discovery.presentation.studios.component.LoadingStudios
import com.zawmyat.anime_discovery.presentation.studios.component.StudioListTile
import com.zawmyat.anime_discovery.presentation.studios.component.StudiosAppBar
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import org.koin.androidx.compose.koinViewModel

@OptIn(FlowPreview::class)
@Composable
fun Studios(
    navController: NavHostController,
    shortcutViewModel: ShortcutViewModel = viewModel(LocalContext.current as MainActivity)
    ) {

    val allStudioViewModel : AllStudioViewModel = koinViewModel<AllStudioViewModel>()
    val studios = allStudioViewModel.allStudios.collectAsLazyPagingItems()

    val context = LocalContext.current


    Scaffold(
        topBar = {
            StudiosAppBar(
                onBack = {
                    if(shortcutViewModel.shortcutName == ShortcutName.STUDIOS) {
                        shortcutViewModel.onShortcutClick(null)
                        navController.popBackStack()
                    } else {
                        navController.popBackStack()
                    }
                }
            )
        }
    ) {

        BackHandler {
            if(shortcutViewModel.shortcutName == ShortcutName.STUDIOS) {
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
                    .padding(horizontal = 12.dp)
            ) {
                items(count = studios.itemCount) {
                        index ->
                    StudioListTile(
                        id = studios[index]?.id ?: 0,
                        name = studios[index]?.name ?: "",
                        favorites = studios[index]?.favourites ?: 0,
                        onClick = {
                            studioId ->
                            navController.navigate("${DetailNavItems.StudioDetailsPage.route}/id=${studioId}")
                        }
                    )
                }

                studios.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                LoadingStudios()
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
