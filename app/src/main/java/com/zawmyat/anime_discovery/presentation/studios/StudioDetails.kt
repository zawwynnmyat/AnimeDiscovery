package com.zawmyat.anime_discovery.presentation.studios

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zawmyat.anime_discovery.R
import com.zawmyat.anime_discovery.data.utils.format
import com.zawmyat.anime_discovery.presentation.characters.component.MediaGridCell
import com.zawmyat.anime_discovery.presentation.component.AnimeGridCell
import com.zawmyat.anime_discovery.presentation.component.LoadingMediaGrid
import com.zawmyat.anime_discovery.presentation.component.shimmerLoadingAnimation
import com.zawmyat.anime_discovery.presentation.details.components.AppBar
import com.zawmyat.anime_discovery.presentation.details.components.TitleWithSeeAll
import com.zawmyat.anime_discovery.presentation.navigation.DetailNavItems
import com.zawmyat.anime_discovery.presentation.reviews.component.AnchoredDraggableCardState
import com.zawmyat.anime_discovery.presentation.staffs.component.LoadingStaffDetails
import com.zawmyat.anime_discovery.presentation.studios.component.LoadingStudioDetails
import com.zawmyat.anime_discovery.presentation.studios.component.StudioDetailAppBar
import com.zawmyat.anime_discovery.presentation.studios.component.StudioMediaGridCell
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun StudioDetails(
    navController: NavHostController,
    id : Int
) {

    val studioDetailsViewModel : StudioDetailsViewModel = koinViewModel<StudioDetailsViewModel>(
        parameters = {
            parametersOf(id)
        }
    )

    val studioDetails by studioDetailsViewModel.studio.collectAsState()
    val isLoadingStudioDetails by studioDetailsViewModel.loading.collectAsState()

    if(isLoadingStudioDetails) {

        LoadingStudioDetails(navController = navController)

    } else {

        Scaffold(
            topBar = {
                StudioDetailAppBar(studioName = studioDetails?.name ?: "") {
                    navController.popBackStack()
                }
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {


                Column(
                    horizontalAlignment = Alignment.End
                ) {

                    Row(
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_favorite_24),
                            contentDescription = null,
                            tint = Color(0xffdb2e0b)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = format(studioDetails?.favourites ?: 0)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    studioDetails?.media?.nodes?.let {
                            mediaNode ->

                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(100.dp),
                            modifier = Modifier.padding(horizontal = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {


                            items(mediaNode.size) {
                                    index ->
                                mediaNode[index]?.let {

                                    StudioMediaGridCell(
                                        title = it.title?.english ?: "",
                                        coverPhoto = it.coverImage?.large ?: "",
                                        format = it.format?.name ?: "",
                                        id = it.id
                                    ) {
                                            clickedMediaId ->
                                        navController.navigate("${DetailNavItems.AnimeDetailPage.route}/id=${clickedMediaId}")
                                    }

                                }
                            }
                        }
                    }

                }


            }
        }


    }


}




