package com.zawmyat.anime_discovery.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zawmyat.anime_discovery.MediaStaffListQuery
import com.zawmyat.anime_discovery.data.pagination.MediaStaffListPagingSource
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class MediaStaffListViewModel(
    private val repository: MainRepository,
    private val mediaId: Int
) : ViewModel() {

    var staffList : Flow<PagingData<MediaStaffListQuery.Edge>> = emptyFlow()

    init {
        staffList = repository.getPaginatedStaffList(mediaId = mediaId).cachedIn(viewModelScope)
    }

}