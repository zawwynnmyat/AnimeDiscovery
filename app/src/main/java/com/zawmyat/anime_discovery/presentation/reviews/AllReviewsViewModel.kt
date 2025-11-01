package com.zawmyat.anime_discovery.presentation.reviews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zawmyat.anime_discovery.ReviewsQuery
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow

class AllReviewsViewModel(
    private val repository: MainRepository
) : ViewModel() {

    var allReviews : Flow<PagingData<ReviewsQuery.Review>> = emptyFlow()

    init {
        allReviews = repository.getAllReviews().cachedIn(viewModelScope)
    }

}