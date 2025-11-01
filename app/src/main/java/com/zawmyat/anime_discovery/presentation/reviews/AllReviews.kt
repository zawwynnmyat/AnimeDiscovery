package com.zawmyat.anime_discovery.presentation.reviews

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.PopUpToBuilder
import androidx.navigation.get
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zawmyat.anime_discovery.R
import com.zawmyat.anime_discovery.data.utils.format
import com.zawmyat.anime_discovery.data.utils.getDateString
import com.zawmyat.anime_discovery.presentation.base.MainActivity
import com.zawmyat.anime_discovery.presentation.home.HomeViewModel
import com.zawmyat.anime_discovery.presentation.navigation.DetailNavItems
import com.zawmyat.anime_discovery.presentation.reviews.component.LoadingReviews
import com.zawmyat.anime_discovery.presentation.reviews.component.ReviewListTile
import com.zawmyat.anime_discovery.presentation.reviews.component.ReviewsAppBar
import com.zawmyat.anime_discovery.presentation.shortcut.ShortcutName
import com.zawmyat.anime_discovery.presentation.shortcut.ShortcutViewModel
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale


@Composable
fun AllReviews(
    navController: NavHostController,
    shortcutViewModel: ShortcutViewModel = viewModel(LocalContext.current as MainActivity)
) {

    val allReviewsViewModel : AllReviewsViewModel = koinViewModel<AllReviewsViewModel>()
    val reviews = allReviewsViewModel.allReviews.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            ReviewsAppBar(
                onBack = {
                    if(shortcutViewModel.shortcutName == ShortcutName.REVIEWS) {
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
            if(shortcutViewModel.shortcutName == ShortcutName.REVIEWS) {
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
            ) {
                items(count = reviews.itemCount) {
                    index ->
                    ReviewListTile(
                        id = reviews[index]?.id ?: 0,
                        bannerImage = reviews[index]?.media?.bannerImage ?: "",
                        mediaTitleRomanji = reviews[index]?.media?.title?.romaji ?: "",
                        mediaFormat = reviews[index]?.media?.format?.name ?: "",
                        reviewerName = reviews[index]?.user?.name ?: "",
                        reviewerAvatar = reviews[index]?.user?.avatar?.medium ?: "",
                        summary = reviews[index]?.summary ?: "",
                        upVote = reviews[index]?.rating ?: 0,
                        totalVote = reviews[index]?.ratingAmount ?: 0,
                        score = reviews[index]?.score ?: 0,
                        timeStamp = reviews[index]?.createdAt ?: 0,
                        onClick = {
                            clickedId ->

                            navController.navigate(
                                DetailNavItems.ReviewDetailsPage.route + "/review_id=${reviews[index]?.id}"
                            )

                        }
                    )
                }

                reviews.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                LoadingReviews()
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





