package com.zawmyat.anime_discovery.presentation.reviews

import android.text.SpannableStringBuilder
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableDefaults
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zawmyat.anime_discovery.R
import org.koin.androidx.compose.koinViewModel
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zawmyat.anime_discovery.data.utils.getDateString
import com.zawmyat.anime_discovery.data.utils.simpleDateFormat
import com.zawmyat.anime_discovery.data.utils.toAnnotatedString
import com.zawmyat.anime_discovery.presentation.navigation.DetailNavItems
import com.zawmyat.anime_discovery.presentation.reviews.component.AnchoredDraggableCardState
import com.zawmyat.anime_discovery.presentation.reviews.component.LoadingReviewDetails
import org.koin.core.parameter.parametersOf
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMotionApi::class, ExperimentalFoundationApi::class)
@Composable
fun ReviewDetails(
    navController: NavController,
    reviewId: Int,
) {

    val reviewViewModel: ReviewViewModel = koinViewModel<ReviewViewModel>(
        parameters = {
            parametersOf(reviewId)
        }
    )

    val details by reviewViewModel.reviewDetail.collectAsStateWithLifecycle()
    val loading by reviewViewModel.isLoadingReviewDetail.collectAsStateWithLifecycle()

    val context = LocalContext.current


    Scaffold(modifier = Modifier.fillMaxSize()) {
        
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it),
            contentAlignment = Alignment.Center
        ) {

            if(loading) {
                LoadingReviewDetails(navController = navController)
            } else {
                ReviewDetailsMotionLayout(
                    bannerImage = details?.media?.bannerImage ?: "",
                    userAvatar = details?.user?.avatar?.large ?: "",
                    userName = details?.user?.name ?: "",
                    createdAt = details?.createdAt ?: 0,
                    body = details?.body ?: "",
                    mediaTitle = details?.media?.title?.english ?: "N/A",
                    type = details?.media?.type?.name ?: "N/A",
                    summary = details?.summary ?: "N/A",
                    score = details?.score ?: 0,
                    onLikeClick = {

                    },
                    onDislikeClick = {

                    },
                    navController = navController
                )
            }

        }
    }
}


@OptIn(ExperimentalMotionApi::class, ExperimentalFoundationApi::class)
@Composable
fun ReviewDetailsMotionLayout(
    bannerImage: String,
    userAvatar: String,
    userName: String,
    createdAt: Int,
    body : String,
    type: String,
    mediaTitle: String,
    summary: String,
    navController: NavController,
    score: Int,
    onLikeClick : () -> Unit,
    onDislikeClick : () -> Unit
) {

    val context = LocalContext.current

    val spannableString = SpannableStringBuilder(body).toString()
    val spanned = HtmlCompat.fromHtml(spannableString, HtmlCompat.FROM_HTML_MODE_COMPACT)

    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.review_details_motion_scence)
            .readBytes()
            .decodeToString()
    }


    val draggedDownAnchorTop = with(LocalDensity.current) {
        200.dp.toPx()
    }

    val anchors = DraggableAnchors {
        AnchoredDraggableCardState.DRAGGED_DOWN at draggedDownAnchorTop
        AnchoredDraggableCardState.DRAGGED_UP at 0f
    }

    val density = LocalDensity.current
    val anchoredDraggableState = remember {
        AnchoredDraggableState<AnchoredDraggableCardState>(
            initialValue = AnchoredDraggableCardState.DRAGGED_DOWN
        )
    }
//    val anchoredDraggableState = remember {
//        AnchoredDraggableState(
//            initialValue = AnchoredDraggableCardState.DRAGGED_DOWN,
//            anchors = anchors,
//            positionalThreshold = { distance: Float -> distance * 0.5f },
//            velocityThreshold = { with(density) { 100.dp.toPx() } },
//            animationSpec = tween(),
//        )
//    }

    val offset = if (anchoredDraggableState.offset.isNaN()) 0f else anchoredDraggableState.offset
    val progress = (1 - (offset / draggedDownAnchorTop)).coerceIn(0f, 1f)

    // update anchors in composition - added 31 Oct
    LaunchedEffect(draggedDownAnchorTop) {
        anchoredDraggableState.updateAnchors(
            DraggableAnchors {
                AnchoredDraggableCardState.DRAGGED_DOWN at draggedDownAnchorTop
                AnchoredDraggableCardState.DRAGGED_UP at 0f
            }
        )
    }

    // 3) create FlingBehavior via AnchoredDraggableDefaults
    val flingBehavior = AnchoredDraggableDefaults.flingBehavior(anchoredDraggableState)


    val connection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                return if (delta < 0f) anchoredDraggableState.dispatchRawDelta(delta).toOffset()
                else Offset.Zero
            }

            override fun onPostScroll(consumed: Offset, available: Offset, source: NestedScrollSource): Offset {
                val delta = available.y
                return anchoredDraggableState.dispatchRawDelta(delta).toOffset()
            }

            // optional: you can call settle() after nested fling is processed if you want
            private fun Float.toOffset() = Offset(0f, this)
        }
    }

//    val connection = remember {
//        object : NestedScrollConnection {
//
//            override fun onPreScroll( // Desides if use the sroll for parent (Swipe) or pass it to the childern
//                available: Offset,
//                source: NestedScrollSource
//            ): Offset {
//                val delta = available.y
//                return if (delta < 0) {
//                    anchoredDraggableState.dispatchRawDelta(delta).toOffset()
//                } else {
//                    Offset.Zero
//                }
//            }
//
//            override fun onPostScroll( // If there is any leftover sroll from childern, let's try to use it on parent swipe
//                consumed: Offset,
//                available: Offset,
//                source: NestedScrollSource
//            ): Offset {
//                val delta = available.y
//                return anchoredDraggableState.dispatchRawDelta(delta).toOffset()
//            }
//
//            override suspend fun onPostFling( // Lets's try to use fling on parent and pass all leftover to childern
//                consumed: Velocity,
//                available: Velocity
//            ): Velocity {
//                anchoredDraggableState.offset.toOffset()
//                return super.onPostFling(consumed, available)
//            }
//
//            private fun Float.toOffset() = Offset(0f, this)
//        }
//    }

    val scrollState = rememberScrollState()

    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        progress = progress,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xff656666))
    ) {


        AsyncImage(
            model = ImageRequest
                .Builder(context)
                .data(bannerImage)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .layoutId("coverPhoto")
                .clickable {
                    val encodedUrl =
                        URLEncoder.encode(bannerImage, StandardCharsets.UTF_8.toString())
                    navController.navigate("${DetailNavItems.PhotoPage.route}/photo_url=${encodedUrl}/name=review_cover")
                },
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Black.copy(0.4f))
                .layoutId("navigationIcon")
        ) {
            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back_ios_new_24),
                    contentDescription = null
                )
            }
        }

        val contentBoxProperties = motionProperties(id = "contentBox")

        Box(
            modifier = Modifier
                .anchoredDraggable(
                    anchoredDraggableState,
                    Orientation.Vertical,
                    flingBehavior = flingBehavior //31 Oct added
                )
                .nestedScroll(connection = connection)
                .fillMaxHeight()
                .shadow(elevation = 20.dp)
                .background(
                    MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(
                        topStart = contentBoxProperties.value.int("borderRadius").dp,
                        topEnd = contentBoxProperties.value.int("borderRadius").dp
                    )
                )
                .layoutId("contentBox")
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 12.dp, end = 12.dp, top = 12.dp)
            ) {

                Text(
                    text = "$type Review",
                    fontSize = 10.sp
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "Review of ${mediaTitle} by ${userName}",
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = summary,
                    fontSize = 10.sp
                )

                Spacer(modifier = Modifier.height(15.dp))

                Row {
                    AsyncImage(
                        model = ImageRequest
                            .Builder(context = context)
                            .data(userAvatar ?: "")
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .height(50.dp)
                            .width(50.dp)
                            .clickable {
                                val encodedUrl =
                                    URLEncoder.encode(userAvatar, StandardCharsets.UTF_8.toString())
                                navController.navigate("${DetailNavItems.PhotoPage.route}/photo_url=${encodedUrl}/name=${userName}")
                            }
                    )

                    Spacer(modifier = Modifier.width(8.dp))


                    Column {

                        Text(
                            text = userName,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = getDateString(createdAt),
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState)
                ) {

                    Text(
                        text = spanned.toAnnotatedString(),
                        fontSize = 13.sp,
                        textAlign = TextAlign.Justify
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            Text(
                                text = "$score / 100",
                                fontSize = 20.sp,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 5.dp),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Do you like this review?",
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xff0eb334))
                                .clickable {
                                    onLikeClick()
                                }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.thumb_up_24),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.width(10.dp))
                        
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xffdb2e0b))
                                .clickable {
                                    onDislikeClick()
                                }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.thumb_down_alt_24),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.padding(5.dp)
                            )
                        }
                    }


                    Spacer(modifier = Modifier.height(50.dp))

                }
            }
        }
    }
}

