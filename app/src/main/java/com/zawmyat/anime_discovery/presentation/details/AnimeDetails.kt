package com.zawmyat.anime_discovery.presentation.details

import android.content.Context
import android.content.Intent
import android.graphics.Paint.Align
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.SpannableStringBuilder
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.AnchoredDraggableDefaults
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.gestures.snapTo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
//import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.core.content.ContextCompat.startActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.get
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieEntry
import com.zawmyat.anime_discovery.MediaQuery
import com.zawmyat.anime_discovery.R
import com.zawmyat.anime_discovery.data.utils.format
import com.zawmyat.anime_discovery.data.utils.navigateToWebSite
import com.zawmyat.anime_discovery.data.utils.toAnnotatedString
import com.zawmyat.anime_discovery.presentation.characters.component.MediaGridCell
import com.zawmyat.anime_discovery.presentation.component.AnimeListTile
import com.zawmyat.anime_discovery.presentation.component.MangaCard
import com.zawmyat.anime_discovery.presentation.component.Title
import com.zawmyat.anime_discovery.presentation.component.getGenreColor
import com.zawmyat.anime_discovery.presentation.details.components.CharactersForDetail
import com.zawmyat.anime_discovery.presentation.details.components.Description
import com.zawmyat.anime_discovery.presentation.details.components.ExternalLinks
import com.zawmyat.anime_discovery.presentation.details.components.GenreDetails
import com.zawmyat.anime_discovery.presentation.details.components.Information
import com.zawmyat.anime_discovery.presentation.details.components.LoadingAnimeDetails
import com.zawmyat.anime_discovery.presentation.details.components.RecommendedList
import com.zawmyat.anime_discovery.presentation.details.components.RecommendedListTile
import com.zawmyat.anime_discovery.presentation.details.components.Relations
import com.zawmyat.anime_discovery.presentation.details.components.ScoreDistributionBarChart
import com.zawmyat.anime_discovery.presentation.details.components.ScoreDistributionSection
import com.zawmyat.anime_discovery.presentation.details.components.Staffs
import com.zawmyat.anime_discovery.presentation.details.components.StatisticsListTile
import com.zawmyat.anime_discovery.presentation.details.components.StatusDistributionPieChart
import com.zawmyat.anime_discovery.presentation.details.components.StatusDistributionSection
import com.zawmyat.anime_discovery.presentation.details.components.TagCell
import com.zawmyat.anime_discovery.presentation.details.components.TitleWithSeeAll
import com.zawmyat.anime_discovery.presentation.home.HomeViewModel
import com.zawmyat.anime_discovery.presentation.navigation.DetailNavItems
import com.zawmyat.anime_discovery.presentation.reviews.component.AnchoredDraggableCardState
import com.zawmyat.anime_discovery.type.CountryCode
import com.zawmyat.anime_discovery.type.FuzzyDate
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@OptIn(ExperimentalMotionApi::class, ExperimentalFoundationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun AnimeDetails(
    navController: NavController,
    id: Int,
) {

    val animeDetailViewModel: AnimeDetailsViewModel = koinViewModel<AnimeDetailsViewModel>(
        parameters = {
            parametersOf(id)
        }
    )

    val mediaDetail by animeDetailViewModel.mediaDetail.collectAsStateWithLifecycle()
    val isLoading by animeDetailViewModel.isLoadingMediaDetail.collectAsStateWithLifecycle()
    val isRefreshing by animeDetailViewModel.isRefreshingMediaDetail.collectAsStateWithLifecycle()

    val state = rememberPullToRefreshState()

//    if(state.isRefreshing) {
//
//        LaunchedEffect(true) {
//            animeDetailViewModel.refreshMediaDetail(id = id)
//            state.endRefresh()
//        }
//
//    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
               // .nestedScroll(connection = state.nestedScrollConnection)
        ) {

            if(isLoading) {

                LoadingAnimeDetails(
                    navController = navController
                )

            } else {

               DetailMotionLayout(
                   id = mediaDetail?.id ?: 0,
                   bannerImage = mediaDetail?.bannerImage ?: "",
                   coverImage = mediaDetail?.coverImage?.large ?: "",
                   title = mediaDetail?.title?.english ?: "N/A",
                   titleRomanji = mediaDetail?.title?.romaji ?: "N/A",
                   titleNative = mediaDetail?.title?.native ?: "N/A",
                   format = mediaDetail?.format?.name ?: "",
                   favorites = mediaDetail?.favourites ?: 0,
                   startDay = mediaDetail?.startDate?.day ?: 0,
                   startMonth = mediaDetail?.startDate?.month ?: 0,
                   startYear = mediaDetail?.seasonYear ?: 0,
                   endDay = mediaDetail?.endDate?.day ?: 0,
                   endMonth = mediaDetail?.endDate?.month ?: 0,
                   endYear = mediaDetail?.endDate?.year ?: 0,
                   description = mediaDetail?.description ?: "No Description",
                   genres = mediaDetail?.genres?.filterNotNull() ?: emptyList(),
                   characters = mediaDetail?.characters,
                   synonyms = mediaDetail?.synonyms?.filterNotNull() ?: emptyList(),
                   status = mediaDetail?.status?.name ?: "N/A",
                   countryOfOrigin = mediaDetail?.countryOfOrigin.toString() ?: "",
                   average = mediaDetail?.averageScore ?: 0,
                   mean = mediaDetail?.meanScore ?: 0,
                   popularity = mediaDetail?.popularity ?: 0,
                   episodes = mediaDetail?.episodes ?: 0,
                   recommendations = mediaDetail?.recommendations,
                   externalLinks = mediaDetail?.externalLinks,
                   staff = mediaDetail?.staff,
                   tags = mediaDetail?.tags?.filterNotNull() ?: emptyList(),
                   relations = mediaDetail?.relations,
                   scoreDistribution = mediaDetail?.stats?.scoreDistribution?.filterNotNull() ?: emptyList(),
                   statusDistribution = mediaDetail?.stats?.statusDistribution?.filterNotNull() ?: emptyList(),
                   navController = navController,
               )

            }

//            PullToRefreshContainer(
//                state = state,
//                modifier = Modifier.align(Alignment.TopCenter)
//            )
            
        }
    }
}

@OptIn(ExperimentalMotionApi::class, ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun DetailMotionLayout(
    id: Int,
    bannerImage: String,
    coverImage: String,
    title : String,
    titleRomanji: String,
    titleNative: String,
    format: String,
    favorites: Int,
    startDay: Int,
    startMonth: Int,
    startYear: Int,
    endDay: Int,
    endMonth: Int,
    endYear: Int,
    description: String,
    genres: List<String>,
    characters : MediaQuery.Characters?,
    synonyms: List<String>,
    status: String,
    countryOfOrigin: String,
    average : Int,
    mean : Int,
    popularity : Int,
    episodes: Int,
    recommendations: MediaQuery.Recommendations?,
    externalLinks: List<MediaQuery.ExternalLink?>?,
    staff: MediaQuery.Staff?,
    tags: List<MediaQuery.Tag?>,
    relations: MediaQuery.Relations?,
    scoreDistribution: List<MediaQuery.ScoreDistribution?>,
    statusDistribution: List<MediaQuery.StatusDistribution?>,
    navController: NavController,
) {


    val context = LocalContext.current

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val scrollState = rememberScrollState()

    val motionScene = rememberSaveable {
        context.resources
            .openRawResource(R.raw.anime_details_motion_scene)
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

    val offset = if (anchoredDraggableState.offset.isNaN()) 0f else anchoredDraggableState.offset

    val progress = (1 - (offset / draggedDownAnchorTop)).coerceIn(0f, 1f)

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

    var isSpoilersShow by rememberSaveable {
        mutableStateOf(false)
    }

    val clipboardManager = LocalClipboardManager.current

    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        progress = progress,
        modifier = Modifier
            .fillMaxSize()
            .anchoredDraggable(
                state = anchoredDraggableState,
                orientation = Orientation.Vertical,
                flingBehavior = flingBehavior // 31 Oct
            )
            .nestedScroll(connection = connection)
    ) {

        val coverPhotoProperties = motionProperties("coverPhoto")

        AsyncImage(
            model = ImageRequest
                .Builder(context)
                .data(bannerImage)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clickable {
                    val encodedUrl =
                        URLEncoder.encode(bannerImage, StandardCharsets.UTF_8.toString())
                    navController.navigate("${DetailNavItems.PhotoPage.route}/photo_url=${encodedUrl}/name=${title + " Banner"}")
                }
                .layoutId("banner")
        )

        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
                .layoutId("bannerFilter")
        )

        Box(
            modifier = Modifier
                .layoutId("appBar")
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
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

                IconButton(
                    onClick = {
                        val text = title + "(${titleRomanji})\n\n${description}"
                        val sendIntent = Intent(Intent.ACTION_SEND).apply {
                            putExtra(Intent.EXTRA_TEXT, text)
                            type = "text/plain"
                        }

                        val shareIntent = Intent.createChooser(sendIntent, null)

                        startActivity(context, shareIntent, null)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ios_share_24),
                        contentDescription = null
                    )
                }
            }
        }

        AsyncImage(
            model = ImageRequest
                .Builder(context)
                .data(coverImage)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clickable {
                    val encodedUrl =
                        URLEncoder.encode(coverImage, StandardCharsets.UTF_8.toString())
                    navController.navigate("${DetailNavItems.PhotoPage.route}/photo_url=${encodedUrl}/name=${title + " Cover"}")
                }
                .clip(RoundedCornerShape(coverPhotoProperties.value.int("borderRadius")))
                .layoutId("coverPhoto")
        )

        Text(
            text = title,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.layoutId("title")
        )

        Icon(
            painter = painterResource(
                id = if(format == "Manga") {
                    R.drawable.book
                } else {
                    R.drawable.ic_local_movies_24
                }
            ),
            contentDescription = null,
            modifier = Modifier.layoutId("formatIcon"),
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            text = format,
            fontSize = 14.sp,
            modifier = Modifier.layoutId("formatText"),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_favorite_24),
            contentDescription = null,
            modifier = Modifier.layoutId("favoriteIcon"),
            tint = Color(0xffdb2e0b)
        )

        Text(
            text = format(favorites),
            fontSize = 14.sp,
            modifier = Modifier.layoutId("favoriteText"),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_calendar_month_24),
            contentDescription = null,
            modifier = Modifier.layoutId("yearIcon"),
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            text = startYear.toString(),
            fontSize = 14.sp,
            modifier = Modifier.layoutId("yearText"),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .anchoredDraggable(
                    state = anchoredDraggableState,
                    orientation = Orientation.Vertical
                )
                .verticalScroll(scrollState)
                .layoutId("content")
        ) {


            GenreDetails(
                genres = genres,
                limit = genres.size
            )





            Description(description = description)

            Spacer(modifier = Modifier.height(10.dp))

            CharactersForDetail(
                characters = characters,
                context = context,
                onSeeAllClick = {
                   navController.navigate("${DetailNavItems.MediaCharacterListPage.route}/mediaId=${id}")
                },
                onCharacterClick = {
                    characterId ->
                    navController.navigate("${DetailNavItems.CharacterDetailsPage.route}/id=${characterId}")
                }
            )

            Information(
                titleRomanji = titleRomanji,
                titleEnglish = title,
                titleNative = titleNative,
                synonyms = synonyms,
                format = format,
                status = status,
                countryOfOrigin = countryOfOrigin,
                startDay = startDay,
                startMonth = startMonth,
                startYear = startYear,
                endDay = endDay,
                endMonth = endMonth,
                endYear = endYear,
                episodes = episodes
            )

            StatisticsListTile(
                average = average,
                mean = mean,
                popularity = popularity,
                favorites = favorites
            )

            Staffs(
                staff = staff,
                context = context,
                onStaffClick = {
                   staffId ->
                     navController.navigate("${DetailNavItems.StaffDetailsPage.route}/id=${staffId}")
                },
                onSeeAllClick = {
                    navController.navigate("${DetailNavItems.MediaStaffListPage.route}/mediaId=${id}")
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tags"
                )

                TextButton(
                    onClick = {
                        isSpoilersShow = !isSpoilersShow
                    }
                ) {
                    Text(
                        text = if(isSpoilersShow) "Hide Spoilers" else  "Show Spoilers",
                        color = MaterialTheme.colorScheme.primary
                    )
                }

            }

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tags.forEach {

                    if(isSpoilersShow) {
                        TagCell(
                            tag = it,
                            onTagClick = {
                                tagName ->
                                navController.navigate("${DetailNavItems.TagMatchedMediaPage.route}/tag=${tagName}")
                            }
                        )
                    } else {

                        if((it?.isMediaSpoiler ?: false) == false) {
                            TagCell(
                                tag = it,
                                onTagClick = {
                                    tagName ->
                                    navController.navigate("${DetailNavItems.TagMatchedMediaPage.route}/tag=${tagName}")
                                }
                            )
                        }
                    }
                }
            }


            Text(
                text = "Long press to see tag details",
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                fontSize = 10.sp,
                modifier = Modifier.padding(top = 8.dp)
            )

            Relations(
                relations = relations
            ) {
                clickedId ->
                navController.navigate("${DetailNavItems.AnimeDetailPage.route}/id=${clickedId}")
            }

            RecommendedList(
                recommendations = recommendations,
                onItemClick = {
                    clickedId ->

                    navController.navigate("${DetailNavItems.AnimeDetailPage.route}/id=${clickedId}")

                }
            )

            ScoreDistributionSection(
                scoreDistribution = scoreDistribution
            )

            StatusDistributionSection(
                statusDistribution = statusDistribution
            )

            ExternalLinks(
                externalLinks = externalLinks,
                context = context,
                onClick = {
                    clickedUrl ->
                    navigateToWebSite(context = context, siteUrl = clickedUrl)
                },
                onLongClick = {
                    clickedUrl ->
                    clipboardManager.setText(AnnotatedString(text = clickedUrl))

                    Toast.makeText(
                        context,
                        "Successfully copied the link.",
                        Toast.LENGTH_LONG
                    ).show()

                }
            )


        }

    }

}











