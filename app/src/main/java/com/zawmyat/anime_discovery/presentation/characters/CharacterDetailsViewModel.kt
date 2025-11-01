package com.zawmyat.anime_discovery.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zawmyat.anime_discovery.CharacterDetailsQuery
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CharacterDetailsViewModel(
    private val mainRepository: MainRepository,
    private val id: Int
) : ViewModel() {

    private val _character = MutableStateFlow<CharacterDetailsQuery.Character?>(null)
    val character : StateFlow<CharacterDetailsQuery.Character?> get() = _character

    private val _loading = MutableStateFlow<Boolean>(false)
    val loading : StateFlow<Boolean> get() = _loading

    init {
        getCharacterDetails(id = id)
    }

    fun getCharacterDetails(id: Int) {
        viewModelScope.launch {

            mainRepository
                .getCharacterDetails(id)
                .onStart {
                    _loading.value = true
                }.catch {
                        exception ->
                    _loading.value = false
                }.collect {
                    _character.value = it
                    _loading.value = false
                }

        }
    }

}