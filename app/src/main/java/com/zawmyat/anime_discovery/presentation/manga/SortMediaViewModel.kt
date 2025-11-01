package com.zawmyat.anime_discovery.presentation.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zawmyat.anime_discovery.SortMediaQuery
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import com.zawmyat.anime_discovery.type.MediaSort
import com.zawmyat.anime_discovery.type.MediaType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class SortMediaViewModel(
    private val repository: MainRepository,
    private val sort: String,
    private val type: String
) : ViewModel() {

    var sortedMedias : Flow<PagingData<SortMediaQuery.Medium>> = emptyFlow()

    init {

        val mediaSort = when(sort) {
            "Top Popular" -> MediaSort.POPULARITY_DESC
            "Top 100" -> MediaSort.SCORE_DESC
            else -> MediaSort.POPULARITY
        }

        val mediaType = when(type) {
            "ANIME" -> MediaType.ANIME
            "MANGA" -> MediaType.MANGA
            else -> MediaType.ANIME
        }

        sortedMedias = repository.getPaginatedSortedMedia(sort = mediaSort, type = mediaType).cachedIn(viewModelScope)
    }
}