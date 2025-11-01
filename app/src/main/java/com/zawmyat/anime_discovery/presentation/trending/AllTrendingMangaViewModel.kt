package com.zawmyat.anime_discovery.presentation.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zawmyat.anime_discovery.TrendingAnimeHomeQuery
import com.zawmyat.anime_discovery.TrendingMangaHomeQuery
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class AllTrendingMangaViewModel(
    private val repository: MainRepository
) : ViewModel() {

    var allTrendingMangas : Flow<PagingData<TrendingMangaHomeQuery.Medium>> = emptyFlow()

    init {
        allTrendingMangas = repository.getAllTrendingManga().cachedIn(viewModelScope)
    }

}