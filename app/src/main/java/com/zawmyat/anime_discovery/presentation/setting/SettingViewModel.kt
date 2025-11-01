package com.zawmyat.anime_discovery.presentation.setting

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zawmyat.anime_discovery.domain.repository.SettingRepository
import com.zawmyat.anime_discovery.presentation.navigation.Graph
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingViewModel(
    private val settingRepository: SettingRepository
) : ViewModel() {

    private val _isLoadedTheme = MutableStateFlow<Boolean>(false)
    val isLoadedTheme : StateFlow<Boolean> get() = _isLoadedTheme

    private val _currentTheme = MutableStateFlow<String>("")
    val currentTheme : StateFlow<String> = _currentTheme

    private val _isLoadedOnboardingStatus : MutableState<Boolean> = mutableStateOf(false)
    val isLoadedOnboardStatus : State<Boolean> = _isLoadedOnboardingStatus

    private val _onboardingStatus : MutableState<Boolean> = mutableStateOf(false)
    val onboardingStatus : State<Boolean> = _onboardingStatus

    private val _initialRoute : MutableState<String> = mutableStateOf(Graph.BOTTOM_NAV)
    val initialRoute : State<String> = _initialRoute

    private val _isLoadedAccessToken = MutableStateFlow<Boolean>(false)
    val isLoadedAccessToken : StateFlow<Boolean> get() = _isLoadedAccessToken

    private val _accessToken = MutableStateFlow<String>("")
    val accessToken : StateFlow<String> = _accessToken

    init {
        getAccessToken()
        getThemeSetting()
        getOnboardingStatus()
    }

    private fun getAccessToken() {
        viewModelScope.launch {
            val accessToken = settingRepository.getAccessToken()

            accessToken.collect {
                tkn ->
                _accessToken.value = tkn
                _isLoadedAccessToken.value = true
            }
        }
    }

    private fun getThemeSetting() {

        viewModelScope.launch {
            val theme = settingRepository.getTheme()

            theme
                .collect {
                    t ->
                    _currentTheme.value = t
                    _isLoadedTheme.value = true
                }
        }
    }

    fun saveThemeSetting(theme: String) {
        viewModelScope.launch {
            settingRepository.saveTheme(value = theme)
        }
    }

    private fun getOnboardingStatus() {

        viewModelScope.launch {
            val onboardingStatus = settingRepository.getOnboardStatus()

            onboardingStatus.collect {
                status ->
                _onboardingStatus.value = status
                if(status) {
                    _initialRoute.value = Graph.BOTTOM_NAV
                } else {
                    _initialRoute.value = Graph.ONBOARDING
                }
                _isLoadedOnboardingStatus.value = true
            }
        }
    }

    fun saveOnboardingStatus(isShow: Boolean) {
        viewModelScope.launch {
            settingRepository.saveOnboardStatus(value = isShow)
        }
    }


}