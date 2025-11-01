package com.zawmyat.anime_discovery.presentation.staffs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zawmyat.anime_discovery.CharacterDetailsQuery
import com.zawmyat.anime_discovery.StaffDetailQuery
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class StaffDetailViewModel(
    private val mainRepository: MainRepository,
    private val id: Int
) : ViewModel() {

    private val _staff = MutableStateFlow<StaffDetailQuery.Staff?>(null)
    val staff : StateFlow<StaffDetailQuery.Staff?> get() = _staff

    private val _loading = MutableStateFlow<Boolean>(false)
    val loading : StateFlow<Boolean> get() = _loading

    init {
        getStaffDetails(id = id)
    }

    fun getStaffDetails(id: Int) {
        viewModelScope.launch {

            mainRepository
                .getStaffDetails(id)
                .onStart {
                    _loading.value = true
                }.catch {
                        exception ->
                    _loading.value = false
                }.collect {
                    _staff.value = it
                    _loading.value = false
                }

        }
    }

}