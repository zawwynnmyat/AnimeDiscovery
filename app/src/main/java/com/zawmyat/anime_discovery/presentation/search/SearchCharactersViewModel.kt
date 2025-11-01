package com.zawmyat.anime_discovery.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zawmyat.anime_discovery.SearchCharactersQuery
import com.zawmyat.anime_discovery.SearchMediaQuery
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onCompletion

class SearchCharactersViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

    val queryFlow = MutableStateFlow("")
    var isSearching = MutableStateFlow(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchResults : Flow<PagingData<SearchCharactersQuery.Character>> = queryFlow
        .flatMapLatest {
                query ->
            mainRepository.getAllSearchedCharacters(
                search = query,
            )
                .onCompletion {
                    isSearching.value = false
                }
        }.cachedIn(viewModelScope)


    fun onSearchQueryChange(newSearch: String) {
        queryFlow.value = newSearch
    }

}