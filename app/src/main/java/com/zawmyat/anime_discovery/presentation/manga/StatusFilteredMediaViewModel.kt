package com.zawmyat.anime_discovery.presentation.manga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zawmyat.anime_discovery.StatusFilterQuery
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import com.zawmyat.anime_discovery.type.MediaStatus
import com.zawmyat.anime_discovery.type.MediaType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow


class StatusFilteredMediaViewModel(
    private val repository: MainRepository,
    private val status: String,
    private val type : String
) : ViewModel() {

    var statusFilteredMedias : Flow<PagingData<StatusFilterQuery.Medium>> = emptyFlow()

    init {

        val mediaStatus = when(status) {
            "Upcoming" -> MediaStatus.NOT_YET_RELEASED
            "Publishing" -> MediaStatus.RELEASING
            "Airing" -> MediaStatus.RELEASING
            else -> MediaStatus.FINISHED
        }

        val mediaType = when(type) {
            "ANIME" -> MediaType.ANIME
            "MANGA" -> MediaType.MANGA
            else -> MediaType.ANIME
        }

        statusFilteredMedias = repository.getPaginatedStatusFilteredMedia(status = mediaStatus, type = mediaType).cachedIn(viewModelScope)
    }
}