package com.zawmyat.anime_discovery.presentation.staffs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.zawmyat.anime_discovery.presentation.component.AnimeGridCell
import com.zawmyat.anime_discovery.presentation.component.LoadingMediaGrid
import com.zawmyat.anime_discovery.presentation.details.components.AppBar
import com.zawmyat.anime_discovery.presentation.details.components.StaffGridCell
import com.zawmyat.anime_discovery.presentation.navigation.DetailNavItems
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AllStaffMedias(
    staffId: Int,
    navController: NavHostController
) {

    val staffMediaListViewModel : StaffMediasListViewModel = koinViewModel<StaffMediasListViewModel>(
        parameters = {
            parametersOf(staffId)
        }
    )

    val mediaList = staffMediaListViewModel.staffMediaList.collectAsLazyPagingItems()

    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppBar(title = "All Medias") {
                navController.popBackStack()
            }
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

            if(mediaList.loadState.refresh == LoadState.Loading) {
                LoadingMediaGrid()
            }  else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(100.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    items(mediaList.itemCount) {
                            index ->
                        mediaList[index]?.let {

                            AnimeGridCell(
                                id = mediaList[index]?.id ?: 0,
                                format = mediaList[index]?.format?.name ?: "N/A",
                                title = mediaList[index]?.title?.userPreferred ?: "N/A",
                                coverPhoto = mediaList[index]?.coverImage?.large ?: "",
                                year = mediaList[index]?.startDate?.year ?: 0,
                                averageScore = mediaList[index]?.averageScore ?: 0
                            ) {
                                    mediaId ->
                                navController.navigate("${DetailNavItems.AnimeDetailPage.route}/id=${mediaId}")
                            }

                        }
                    }

                    mediaList.apply {
                        when {

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