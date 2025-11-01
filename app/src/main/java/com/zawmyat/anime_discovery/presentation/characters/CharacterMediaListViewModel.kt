package com.zawmyat.anime_discovery.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zawmyat.anime_discovery.CharacterMediaListQuery
import com.zawmyat.anime_discovery.MediaStaffListQuery
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class CharacterMediaListViewModel(
    private val repository: MainRepository,
    private val characterId: Int
) : ViewModel() {

    var mediaList : Flow<PagingData<CharacterMediaListQuery.Edge>> = emptyFlow()

    init {
        mediaList = repository.getPaginatedCharacterMediaList(characterId = characterId).cachedIn(viewModelScope)
    }
}