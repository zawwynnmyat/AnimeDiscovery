package com.zawmyat.anime_discovery.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import com.zawmyat.anime_discovery.SearchMediaQuery
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import com.zawmyat.anime_discovery.type.MediaType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class SearchMediaViewModel(
    private val mainRepository: MainRepository,
    private val mediaType: MediaType
) : ViewModel() {

    val queryFlow = MutableStateFlow("")
    var isSearching = MutableStateFlow(false)

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchResults : Flow<PagingData<SearchMediaQuery.Medium>> = queryFlow
        .flatMapLatest {
            query ->
            mainRepository.getAllSearchedMedia(
                search = query,
                mediaType = mediaType
            )
                .onCompletion {
                    isSearching.value = false
                }
        }.cachedIn(viewModelScope)


    fun onSearchQueryChange(newSearch: String) {
        queryFlow.value = newSearch
    }

}