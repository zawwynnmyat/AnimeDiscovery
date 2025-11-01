package com.zawmyat.anime_discovery.presentation.studios

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zawmyat.anime_discovery.CharacterDetailsQuery
import com.zawmyat.anime_discovery.StudioDetailQuery
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class StudioDetailsViewModel(
    private val mainRepository: MainRepository,
    private val id: Int
) : ViewModel() {

    private val _studio = MutableStateFlow<StudioDetailQuery.Studio?>(null)
    val studio : StateFlow<StudioDetailQuery.Studio?> get() = _studio

    private val _loading = MutableStateFlow<Boolean>(false)
    val loading : StateFlow<Boolean> get() = _loading

    init {
        getStudioDetails(id = id)
    }

    fun getStudioDetails(id: Int) {
        viewModelScope.launch {

            mainRepository
                .getStudioDetails(id)
                .onStart {
                    _loading.value = true
                }.catch {
                        exception ->
                    _loading.value = false
                }.collect {
                    _studio.value = it
                    _loading.value = false
                }

        }
    }

}