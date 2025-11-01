package com.zawmyat.anime_discovery.presentation.staffs

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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
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
import com.zawmyat.anime_discovery.presentation.characters.component.LoadingCharacters
import com.zawmyat.anime_discovery.presentation.home.HomeViewModel
import com.zawmyat.anime_discovery.presentation.navigation.DetailNavItems
import com.zawmyat.anime_discovery.presentation.shortcut.ShortcutName
import com.zawmyat.anime_discovery.presentation.shortcut.ShortcutViewModel
import com.zawmyat.anime_discovery.presentation.staffs.component.StaffsAppBar
import com.zawmyat.anime_discovery.presentation.staffs.component.StaffsListTile
import com.zawmyat.anime_discovery.presentation.studios.component.StudiosAppBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun Staffs(
    navController: NavHostController,
    shortcutViewModel: ShortcutViewModel = viewModel(LocalContext.current as MainActivity)
    ) {

    val allStaffsViewModel : AllStaffsViewModel = koinViewModel<AllStaffsViewModel>()
    val staffs = allStaffsViewModel.allStaffs.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            StaffsAppBar(
                onBack = {
                    if(shortcutViewModel.shortcutName == ShortcutName.STAFFS) {
                        shortcutViewModel.onShortcutClick(null)
                        navController.popBackStack()
                    } else {
                        navController.popBackStack()
                    }
                },
                onSearchClick = {
                    navController.navigate(DetailNavItems.SearchStaffs.route)
                }
            )
        }
    ) {

        BackHandler {
            if(shortcutViewModel.shortcutName == ShortcutName.STAFFS) {
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
                items(count = staffs.itemCount) {
                        index ->
                    StaffsListTile(
                        id = staffs[index]?.id ?: 0,
                        name = staffs[index]?.name?.full ?: "N/A",
                        photo = staffs[index]?.image?.large ?: "",
                        favorites = staffs[index]?.favourites ?: 0,
                        gender = staffs[index]?.gender ?: "N/A",
                        age = staffs[index]?.age ?: -1,
                        homeTown = staffs[index]?.homeTown ?: "N/A",
                        bloodType = staffs[index]?.bloodType ?: "N/A",
                        count = index,
                        onClick = {
                            staffId ->
                            navController.navigate("${DetailNavItems.StaffDetailsPage.route}/id=${staffId}")
                        }
                    )
                }

                staffs.apply {
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


