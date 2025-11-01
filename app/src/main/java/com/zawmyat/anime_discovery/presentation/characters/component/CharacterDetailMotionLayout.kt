package com.zawmyat.anime_discovery.presentation.characters.component

import android.util.Log
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.exponentialDecay
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
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
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zawmyat.anime_discovery.CharacterDetailsQuery
import com.zawmyat.anime_discovery.R
import com.zawmyat.anime_discovery.data.utils.format
import com.zawmyat.anime_discovery.presentation.details.components.Description
import com.zawmyat.anime_discovery.presentation.details.components.InfoTile
import com.zawmyat.anime_discovery.presentation.details.components.TitleWithSeeAll
import com.zawmyat.anime_discovery.presentation.navigation.DetailNavItems
import com.zawmyat.anime_discovery.presentation.reviews.component.AnchoredDraggableCardState
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMotionApi::class, ExperimentalFoundationApi::class,
    ExperimentalSharedTransitionApi::class
)
@Composable
fun CharacterDetailMotionLayout(
    id: Int,
    coverImage: String,
    name: String,
    nativeName: String,
    favorites: Int,
    gender: String,
    description: String,
    medias: CharacterDetailsQuery.Media?,
    bloodType: String,
    age: String,
    navController: NavHostController,
) {

    val context = LocalContext.current

    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.character_details_motion_scene)
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


    // 2) update anchors in composition
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
                flingBehavior = flingBehavior //31 Oct added
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
                content = age
            )

            Description(description = description)

            Column(
                modifier = Modifier.padding(vertical = 30.dp)
            ) {

                TitleWithSeeAll(
                    title = "Appeared In",
                    onSeeAllClick = {
                        navController.navigate("${DetailNavItems.CharacterMediaListPage.route}/characterId=${id}")
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))

                AppeardInLazyRow(
                    medias = medias,
                    navController = navController
                )

            }

        }

    }

}