package com.zawmyat.anime_discovery.presentation.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zawmyat.anime_discovery.TrendingAnimeHomeQuery
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class AllTrendingAnimeViewModel(
    private val repository: MainRepository
) : ViewModel() {

    var allTrendingAnimes : Flow<PagingData<TrendingAnimeHomeQuery.Medium>> = emptyFlow()

    init {
        allTrendingAnimes = repository.getAllTrendingAnime().cachedIn(viewModelScope)
    }

}