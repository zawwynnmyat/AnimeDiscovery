package com.zawmyat.anime_discovery.presentation.staffs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zawmyat.anime_discovery.StaffCharactersListQuery
import com.zawmyat.anime_discovery.StaffMediasListQuery
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class StaffMediasListViewModel(
    private val repository: MainRepository,
    private val staffId : Int
) : ViewModel() {

    var staffMediaList : Flow<PagingData<StaffMediasListQuery.Node>> = emptyFlow()

    init {
        staffMediaList = repository.getPaginatedStaffMediasList(staffId = staffId).cachedIn(viewModelScope)
    }

}