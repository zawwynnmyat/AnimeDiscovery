package com.zawmyat.anime_discovery.presentation.staffs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zawmyat.anime_discovery.StaffsQuery
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class AllStaffsViewModel(
    private val repository: MainRepository
) : ViewModel() {

    var allStaffs : Flow<PagingData<StaffsQuery.Staff>> = emptyFlow()

    init {
        allStaffs = repository.getAllStaffs().cachedIn(viewModelScope)
    }

}