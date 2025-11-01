package com.zawmyat.anime_discovery.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zawmyat.anime_discovery.CharactersQuery
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class AllCharactersViewModel(
    private val repository: MainRepository
) : ViewModel() {

    var allCharacters : Flow<PagingData<CharactersQuery.Character>> = emptyFlow()

    init {
        allCharacters = repository.getAllCharacters().cachedIn(viewModelScope)
    }

}