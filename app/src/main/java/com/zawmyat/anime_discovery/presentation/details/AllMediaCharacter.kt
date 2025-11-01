package com.zawmyat.anime_discovery.presentation.details

import android.content.Context
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.zawmyat.anime_discovery.presentation.component.LoadingMediaGrid
import com.zawmyat.anime_discovery.presentation.details.components.AppBar
import com.zawmyat.anime_discovery.presentation.navigation.DetailNavItems
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AllMediaCharacter(
    navController: NavHostController,
    mediaId : Int
) {

    val mediaCharacterListViewModel : MediaCharacterListViewModel = koinViewModel<MediaCharacterListViewModel>(
        parameters = {
            parametersOf(mediaId)
        }
    )

    val characterList = mediaCharacterListViewModel.characterList.collectAsLazyPagingItems()

    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppBar(title = "All Characters") {
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

            if(characterList.loadState.refresh == LoadState.Loading) {
                LoadingMediaGrid()
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(100.dp),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    items(characterList.itemCount) {
                            index ->
                        characterList[index]?.let {

                            CharacterWithVoiceActorGridCell(
                                characterId = characterList[index]?.node?.id ?: 0,
                                characterAvatar = characterList[index]?.node?.image?.large ?: "",
                                characterName = characterList[index]?.node?.name?.full ?: "N/A",
                                characterRole = characterList[index]?.role?.name ?: "N/A",
                                voiceActorId = if((characterList[index]?.voiceActors?.size ?: 0) > 0)
                                    characterList[index]?.voiceActors?.first()?.id ?: 0
                                else 0,
                                voiceActorAvatar = if((characterList[index]?.voiceActors?.size ?: 0) > 0)
                                    characterList[index]?.voiceActors?.first()?.image?.large ?: ""
                                else "",
                                voiceActorName = if((characterList[index]?.voiceActors?.size ?: 0) > 0)
                                    characterList[index]?.voiceActors?.first()?.name?.full ?: ""
                                else "N/A",
                                context = context,
                                onCharacterClick = {
                                        characterId ->
                                    navController.navigate("${DetailNavItems.CharacterDetailsPage.route}/id=${characterId}")
                                }
                            )



                        }
                    }

                    characterList.apply {
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

@Composable
fun CharacterWithVoiceActorGridCell(
    characterId: Int,
    characterAvatar : String,
    characterName : String,
    characterRole: String,
    voiceActorId : Int,
    voiceActorAvatar : String,
    voiceActorName: String,
    context: Context,
    onCharacterClick : (clickedId : Int) -> Unit
) {

    Column(
        modifier = Modifier.clickable {
            onCharacterClick(characterId)
        }
    ) {
        AsyncImage(
            model = ImageRequest
                .Builder(context = context)
                .data(characterAvatar)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .fillMaxWidth()
                .height(165.dp),
            contentScale = ContentScale.Crop
        )

        Text(
            text = characterName,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 12.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = characterRole,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 10.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )

        if(voiceActorName.isNotEmpty()) {
            Spacer(modifier = Modifier.height(3.dp))
        }

        if(voiceActorName.isNotEmpty()) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Start
            ) {
                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest
                            .Builder(context = context)
                            .data(voiceActorAvatar)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .height(40.dp)
                            .width(40.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = voiceActorName,
                    fontSize = 10.sp
                )
            }
        }



    }

}