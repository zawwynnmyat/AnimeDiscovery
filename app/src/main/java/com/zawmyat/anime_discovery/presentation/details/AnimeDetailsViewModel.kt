package com.zawmyat.anime_discovery.presentation.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zawmyat.anime_discovery.MediaQuery
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.launch
import okio.IOException
import org.koin.androidx.compose.get

class AnimeDetailsViewModel(
    private val repository : MainRepository,
    private val id: Int
) : ViewModel() {


    private val _isLoadingMediaDetail = MutableStateFlow<Boolean>(false)
    val isLoadingMediaDetail : StateFlow<Boolean> get() = _isLoadingMediaDetail

    private val _mediaDetail = MutableStateFlow<MediaQuery.Media?>(null)
    val mediaDetail : StateFlow<MediaQuery.Media?> get() = _mediaDetail

    private val _isRefreshingMediaDetail = MutableStateFlow<Boolean>(false)
    val isRefreshingMediaDetail : StateFlow<Boolean> get() = _isRefreshingMediaDetail

    init {
        getMediaDetail(id)
    }

    fun getMediaDetail(id: Int) {
        viewModelScope.launch {
            repository
                .getMediaDetails(id = id)
                .onStart {
                    _isLoadingMediaDetail.value = true
                }.catch {
                    _isLoadingMediaDetail.value = false
                }.collect {
                        review ->
                    _mediaDetail.value = review
                    _isLoadingMediaDetail.value = false
                }
        }
    }

    fun refreshMediaDetail(id: Int) {

//        viewModelScope.launch {
//            _isRefreshingMediaDetail.value = true
//            delay(1000)
//            _isRefreshingMediaDetail.value = false
//        }

        viewModelScope.launch {
            repository
                .getMediaDetails(id = id)
                .onStart {
                    _isRefreshingMediaDetail.value = true
                }
                .retryWhen { cause, attempt ->
                    if(attempt < 3) {
                        delay(2000)
                        return@retryWhen true
                    } else {
                        _isRefreshingMediaDetail.value = false
                        return@retryWhen false
                    }
                }
                .catch {

                }
                .collect {
                        review ->
                    _mediaDetail.value = review
                    _isRefreshingMediaDetail.value = false
                }
        }

    }

}