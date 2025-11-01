package com.zawmyat.anime_discovery.presentation.details

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.zawmyat.anime_discovery.presentation.component.LoadingMediaGrid
import com.zawmyat.anime_discovery.presentation.details.components.AppBar
import com.zawmyat.anime_discovery.presentation.details.components.StaffGridCell
import com.zawmyat.anime_discovery.presentation.navigation.DetailNavItems
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AllMediaStaff(
    navController: NavHostController,
    mediaId : Int
) {

    val mediaStaffListViewModel : MediaStaffListViewModel = koinViewModel<MediaStaffListViewModel>(
        parameters = {
            parametersOf(mediaId)
        }
    )

    val staffList = mediaStaffListViewModel.staffList.collectAsLazyPagingItems()

    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppBar(title = "All Staffs") {
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

            if(staffList.loadState.refresh == LoadState.Loading) {

                LoadingMediaGrid()

            } else {

                LazyVerticalGrid(
                    columns = GridCells.Adaptive(100.dp),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    items(staffList.itemCount) {
                            index ->
                        staffList[index]?.let {

                            StaffGridCell(
                                id = staffList[index]?.node?.id ?: 0,
                                coverPhoto = staffList[index]?.node?.image?.large ?: "",
                                name = staffList[index]?.node?.name?.full ?: "",
                                role = staffList[index]?.role ?: "",
                                context = context
                            ) {
                                    staffId ->
                                navController.navigate("${DetailNavItems.StaffDetailsPage.route}/id=${staffId}")

                            }

                        }
                    }

                    staffList.apply {
                        when {
                            loadState.refresh is LoadState.Loading -> {
                                item {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center,
                                        modifier = Modifier.fillMaxSize()
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
