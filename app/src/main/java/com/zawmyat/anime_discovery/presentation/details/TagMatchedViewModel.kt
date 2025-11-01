package com.zawmyat.anime_discovery.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zawmyat.anime_discovery.TagMatchQuery
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class TagMatchedViewModel(
    private val repository: MainRepository,
    private val tag: String
) : ViewModel() {

    var tagMatchedMedias : Flow<PagingData<TagMatchQuery.Medium>> = emptyFlow()

    init {

        tagMatchedMedias = repository.getPaginatedTagMatchMedia(tag = tag).cachedIn(viewModelScope)
    }

}