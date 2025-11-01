package com.zawmyat.anime_discovery.presentation.studios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zawmyat.anime_discovery.StaffsQuery
import com.zawmyat.anime_discovery.StudiosQuery
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class AllStudioViewModel(
    private val repository: MainRepository
) : ViewModel() {

    var allStudios : Flow<PagingData<StudiosQuery.Studio>> = emptyFlow()

    init {
        allStudios = repository.getAllStudios().cachedIn(viewModelScope)
    }

}