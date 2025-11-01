package com.zawmyat.anime_discovery.presentation.staffs

import android.text.util.Linkify
import android.widget.TextView
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableDefaults
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.core.text.HtmlCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.zawmyat.anime_discovery.R
import com.zawmyat.anime_discovery.StaffDetailQuery
import com.zawmyat.anime_discovery.data.utils.format
import com.zawmyat.anime_discovery.presentation.characters.component.AppeardInLazyRow
import com.zawmyat.anime_discovery.presentation.characters.component.CharacterDetailAppBar
import com.zawmyat.anime_discovery.presentation.characters.component.LoadingCharacterDetails
import com.zawmyat.anime_discovery.presentation.characters.component.LoadingCharacters
import com.zawmyat.anime_discovery.presentation.details.components.AppBar
import com.zawmyat.anime_discovery.presentation.details.components.Description
import com.zawmyat.anime_discovery.presentation.details.components.InfoTile
import com.zawmyat.anime_discovery.presentation.details.components.TitleWithSeeAll
import com.zawmyat.anime_discovery.presentation.navigation.DetailNavItems
import com.zawmyat.anime_discovery.presentation.reviews.component.AnchoredDraggableCardState
import com.zawmyat.anime_discovery.presentation.staffs.component.CharactersForStaff
import com.zawmyat.anime_discovery.presentation.staffs.component.LoadingStaffDetails
import com.zawmyat.anime_discovery.presentation.staffs.component.StaffMediaList
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun StaffDetails(
    navController: NavHostController,
    id : Int
) {

    val staffDetailViewModel : StaffDetailViewModel = koinViewModel<StaffDetailViewModel>(
        parameters = {
            parametersOf(id)
        }
    )

    val staffDetail by staffDetailViewModel.staff.collectAsStateWithLifecycle()
    val isLoading by staffDetailViewModel.loading.collectAsStateWithLifecycle()

    Scaffold(
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            if(isLoading) {
                
                LoadingStaffDetails(navController = navController)

            } else {

                StaffDetailsMotionLayout(
                    id = staffDetail?.id ?: 0,
                    name = staffDetail?.name?.full ?: "N/A",
                    nativeName = staffDetail?.name?.native ?: "N/A",
                    coverImage = staffDetail?.image?.large ?: "",
                    favorites = staffDetail?.favourites ?: 0,
                    gender = staffDetail?.gender ?: "N/A",
                    age = staffDetail?.age ?: 0,
                    bloodType = staffDetail?.bloodType ?: "N/A",
                    homeTown = staffDetail?.homeTown ?: "N/A",
                    description = staffDetail?.description ?: "N/A",
                    language = staffDetail?.languageV2 ?: "N/A",
                    characters = staffDetail?.characters,
                    staffMedias = staffDetail?.staffMedia,
                    navController = navController
                )
                
                
            }


        }
    }
}

//.aab - application app bundle
//.apk
@OptIn(ExperimentalFoundationApi::class, ExperimentalMotionApi::class)
@Composable
fun StaffDetailsMotionLayout(
    id: Int,
    name: String,
    nativeName: String,
    coverImage : String,
    favorites: Int,
    gender: String,
    age: Int,
    bloodType: String,
    homeTown: String,
    description: String,
    language: String,
    characters: StaffDetailQuery.Characters?,
    staffMedias : StaffDetailQuery.StaffMedia?,
    navController: NavHostController
) {

    val context = LocalContext.current

    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.staff_details_motion_scene)
            .readBytes()
            .decodeToString()
    }

    val scrollState = rememberScrollState()

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

    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        progress = progress,
        modifier = Modifier
            .fillMaxSize()
            .anchoredDraggable(
                state = anchoredDraggableState,
                orientation = Orientation.Vertical,
                flingBehavior = flingBehavior //31 Oct Added
            )
            .nestedScroll(connection = connection)
    ) {

        CharacterDetailAppBar(
            modifier = Modifier.layoutId("appBar"),
            onNavIconClick = {
                navController.popBackStack()
            }
        )

        val avatarProperties = motionProperties("avatar")

        AsyncImage(
            model = ImageRequest
                .Builder(context = context)
                .data(coverImage)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .clickable {
                    val encodedUrl =
                        URLEncoder.encode(coverImage, StandardCharsets.UTF_8.toString())
                    navController.navigate("${DetailNavItems.PhotoPage.route}/photo_url=${encodedUrl}/name=${name}")
                }
                .clip(RoundedCornerShape(avatarProperties.value.int("borderRadius")))
                .layoutId("avatar"),
            contentScale = ContentScale.Crop
        )


        Text(
            text = name,
            fontSize = 17.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.layoutId("name"),
            fontWeight = FontWeight.Bold,
        )

        Text(
            text = nativeName,
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.layoutId("nativeName"),
            fontWeight = FontWeight.Bold,
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_favorite_24),
            contentDescription = null,
            tint = Color(0xffdb2e0b),
            modifier = Modifier.layoutId("favIcon")
        )

        Text(
            text = format(favorites),
            modifier = Modifier.layoutId("favText"),
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .layoutId("genderBackgroundBox")
                .background(color = MaterialTheme.colorScheme.primaryContainer)
        )

        Icon(
            painter = when(gender) {
                "Male" -> painterResource(id = R.drawable.male)
                "Female" -> painterResource(id = R.drawable.female)
                else -> painterResource(id = R.drawable.transgender)
            },
            contentDescription = null,
            modifier = Modifier.layoutId("genderIcon")
        )

        Text(
            text = gender,
            modifier = Modifier.layoutId("genderText")
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .layoutId("content")
        ) {


            InfoTile(
                title = "Blood Type",
                content = bloodType
            )

            InfoTile(
                title = "Age",
                content = age.toString()
            )
            
            InfoTile(
                title = "Home Town",
                content = homeTown
            )

            InfoTile(
                title = "Language",
                content = language
            )

            Description(description = description)

            CharactersForStaff(
                characters = characters,
                context = context,
                onCharacterClick = {
                    characterId ->
                      navController.navigate("${DetailNavItems.CharacterDetailsPage.route}/id=${characterId}")
                },
                onSeeAllClick = {
                    navController.navigate("${DetailNavItems.StaffCharacterListPage.route}/staffId=${id}")
                }
            )

            Column(
                modifier = Modifier.padding(vertical = 30.dp)
            ) {

                TitleWithSeeAll(
                    title = "Staff Medias",
                    onSeeAllClick = {
                        navController.navigate("${DetailNavItems.StaffMediasListPage.route}/staffId=${id}")
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))


                StaffMediaList(
                    medias = staffMedias,
                    navController = navController
                )


            }

        }

    }
}