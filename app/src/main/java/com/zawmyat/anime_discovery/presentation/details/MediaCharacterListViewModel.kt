package com.zawmyat.anime_discovery.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zawmyat.anime_discovery.MediaCharacterListQuery
import com.zawmyat.anime_discovery.MediaStaffListQuery
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class MediaCharacterListViewModel(
    private val repository: MainRepository,
    private val mediaId : Int
) : ViewModel() {

    var characterList : Flow<PagingData<MediaCharacterListQuery.Edge>> = emptyFlow()

    init {
        characterList = repository.getPaginatedCharacterList(mediaId = mediaId).cachedIn(viewModelScope)
    }

}