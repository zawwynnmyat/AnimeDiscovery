package com.zawmyat.anime_discovery.presentation.staffs

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
import com.zawmyat.anime_discovery.presentation.component.AnimeGridCell
import com.zawmyat.anime_discovery.presentation.component.LoadingMediaGrid
import com.zawmyat.anime_discovery.presentation.details.CharacterWithVoiceActorGridCell
import com.zawmyat.anime_discovery.presentation.details.components.AppBar
import com.zawmyat.anime_discovery.presentation.navigation.DetailNavItems
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun AllStaffCharacters(
    staffId: Int,
    navController: NavHostController
) {
    
    val staffCharacterListViewModel : StaffCharactersListViewModel = koinViewModel<StaffCharactersListViewModel>(
        parameters = {
            parametersOf(staffId)
        }
    )

    val characterList = staffCharacterListViewModel.staffCharacterList.collectAsLazyPagingItems()

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
                                characterRole = "",
                                voiceActorId = if((characterList[index]?.voiceActors?.size ?: 0) > 0)
                                    characterList[index]?.voiceActors?.first()?.id ?: 0
                                else 0,
                                voiceActorAvatar = if((characterList[index]?.voiceActors?.size ?: 0) > 0)
                                    characterList[index]?.voiceActors?.first()?.image?.large ?: ""
                                else "",
                                voiceActorName = if((characterList[index]?.voiceActors?.size ?: 0) > 0)
                                    characterList[index]?.voiceActors?.first()?.name?.full ?: ""
                                else "",
                                context = context
                            ) {
                                    characterId ->
                                navController.navigate("${DetailNavItems.CharacterDetailsPage.route}/id=${characterId}")
                            }

                        }
                    }

                    characterList.apply {
                        when {

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