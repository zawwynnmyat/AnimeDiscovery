package com.zawmyat.anime_discovery.presentation.characters

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zawmyat.anime_discovery.CharacterDetailsQuery
import com.zawmyat.anime_discovery.R
import com.zawmyat.anime_discovery.data.utils.format
import com.zawmyat.anime_discovery.presentation.characters.component.AppeardInLazyRow
import com.zawmyat.anime_discovery.presentation.characters.component.CharacterDetailAppBar
import com.zawmyat.anime_discovery.presentation.characters.component.CharacterDetailMotionLayout
import com.zawmyat.anime_discovery.presentation.characters.component.LoadingCharacterDetails
import com.zawmyat.anime_discovery.presentation.characters.component.MediaGridCell
import com.zawmyat.anime_discovery.presentation.component.AnimeGridCell
import com.zawmyat.anime_discovery.presentation.details.components.Description
import com.zawmyat.anime_discovery.presentation.details.components.InfoTile
import com.zawmyat.anime_discovery.presentation.details.components.TitleWithSeeAll
import com.zawmyat.anime_discovery.presentation.navigation.DetailNavItems
import com.zawmyat.anime_discovery.presentation.reviews.component.AnchoredDraggableCardState
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMotionApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun CharacterDetails(
    navController: NavHostController,
    id : Int
) {

    val characterDetailViewModel = koinViewModel<CharacterDetailsViewModel>(
        parameters = {
            parametersOf(id)
        }
    )

    val characterDetail by characterDetailViewModel.character.collectAsStateWithLifecycle()
    val isLoading by characterDetailViewModel.loading.collectAsStateWithLifecycle()

    Scaffold(
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {

            if(isLoading) {

                LoadingCharacterDetails(navController = navController)

            } else {

                CharacterDetailMotionLayout(
                    coverImage = characterDetail?.image?.large ?: "",
                    name = characterDetail?.name?.full ?: "N/A",
                    nativeName = characterDetail?.name?.native ?: "N/A",
                    favorites = characterDetail?.favourites ?: 0,
                    gender = characterDetail?.gender ?: "Unknown",
                    description = characterDetail?.description ?: "",
                    medias = characterDetail?.media,
                    bloodType = characterDetail?.bloodType ?: "N/A",
                    age = characterDetail?.age ?: "N/A",
                    id = id,
                    navController = navController
                )

            }
        }
    }
}






