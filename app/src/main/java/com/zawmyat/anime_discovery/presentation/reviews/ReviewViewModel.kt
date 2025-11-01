package com.zawmyat.anime_discovery.presentation.reviews

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zawmyat.anime_discovery.ReviewDetailsQuery
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ReviewViewModel(
    private val mainRepository: MainRepository,
    private val id: Int
) : ViewModel() {

    private val _isLoadingReviewDetail = MutableStateFlow<Boolean>(false)
    val isLoadingReviewDetail : StateFlow<Boolean> get() = _isLoadingReviewDetail

    private val _reviewDetail = MutableStateFlow<ReviewDetailsQuery.Review?>(null)
    val reviewDetail : StateFlow<ReviewDetailsQuery.Review?> get() = _reviewDetail

    init {
        getReviewDetail(id)
    }

    private fun getReviewDetail(reviewId: Int) {
        viewModelScope.launch {
            mainRepository
                .getReviewDetails(id = reviewId)
                .onStart {
                    _isLoadingReviewDetail.value = true
                }.catch {
                    _isLoadingReviewDetail.value = false
                    Log.e("error_loading", it.message.toString())
                }.collect {
                    review ->
                    _reviewDetail.value = review
                    _isLoadingReviewDetail.value = false
                }
        }
    }

}