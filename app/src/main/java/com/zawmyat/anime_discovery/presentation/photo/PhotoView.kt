package com.zawmyat.anime_discovery.presentation.photo

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.zawmyat.anime_discovery.R
import com.zawmyat.anime_discovery.data.DownloaderImpl
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PhotoView(
    photoUrl: String,
    name: String,
    navController: NavController,
    downloadViewModel: DownloadViewModel = koinViewModel<DownloadViewModel>()
) {

    var scale by remember {
        mutableStateOf(1f)
    }

    var offset by remember {
        mutableStateOf(Offset(0f, 0f))
    }

    val context = LocalContext.current

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    downloadViewModel.downloadImage(photoUrl, name)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_download_24),
                    contentDescription = null
                )
            }
        },
        topBar = {
            TopAppBar(
                title = { 
                    Text(text = name)
                },
                navigationIcon = {
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
                },
                modifier = Modifier.background(Color.Transparent)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->

                        scale *= zoom

                        scale = scale.coerceIn(0.5f, 3f)
                    }
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            AsyncImage(
                model = ImageRequest
                    .Builder(context = context)
                    .data(photoUrl)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale,
                        translationX = offset.x,
                        translationY = offset.y
                    )
            )
        }
    }
}