package com.zawmyat.anime_discovery.presentation.staffs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zawmyat.anime_discovery.MediaStaffListQuery
import com.zawmyat.anime_discovery.StaffCharactersListQuery
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class StaffCharactersListViewModel(
    private val repository: MainRepository,
    private val staffId: Int
) : ViewModel() {

    var staffCharacterList : Flow<PagingData<StaffCharactersListQuery.Edge>> = emptyFlow()

    init {
        staffCharacterList = repository.getPaginatedStaffCharactersList(staffId = staffId).cachedIn(viewModelScope)
    }

}