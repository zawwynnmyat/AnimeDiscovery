package com.zawmyat.anime_discovery.presentation.account

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.exception.ApolloException
import com.zawmyat.anime_discovery.ViewerQuery
import com.zawmyat.anime_discovery.data.auth.Constants
import com.zawmyat.anime_discovery.data.auth.Token
import com.zawmyat.anime_discovery.domain.auth.AnilistRepo
import com.zawmyat.anime_discovery.domain.auth.Response
import com.zawmyat.anime_discovery.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AccountViewModel(
    private val aniListRepo : AnilistRepo,
    private val settingRepository: SettingRepository,
    private val token: Token,
    private val client: ApolloClient
) : ViewModel() {

    private val _isLoadingViewer = MutableStateFlow<Boolean>(false)
    val isLoadingViewer : StateFlow<Boolean> get() = _isLoadingViewer

    private val _viewer = MutableStateFlow<ViewerQuery.Viewer?>(null)
    val viewer: StateFlow<ViewerQuery.Viewer?> get() = _viewer

    fun makeAuthRequest(
        clientId: String, clientSecret: String, code: String
    ) = viewModelScope.launch {
        aniListRepo.getAccessToken(
            clientId = clientId,
            clientSecret = clientSecret,
            code = code
        ).collect {
            response ->
            when(response) {
                is Response.Loading -> {
                    Log.d("Anilist", "Sending Auth Request..")
                }
                is Response.Success -> {
                    Log.d("Anilist", "Success Auth Request")
                    Log.d("Anilist", "Access Token is ${response.data.accessToken}")
                    settingRepository.saveAccessToken(value = response.data.accessToken)
                    token.accessToken = response.data.accessToken
                }
                is Response.Failure -> {
                    Log.d("Anilist", response.e.message.toString())
                }
            }
        }
    }


    suspend fun fetchAndSaveOptions()  {

        _isLoadingViewer.value = true

        try {
            val response = client
                .query(ViewerQuery())
                .execute()

            response.data?.let {
                    data ->
                data.Viewer?.let {
                        viewer ->
                   _viewer.value = viewer
                    _isLoadingViewer.value = false
                }
            } ?: Log.d("Anilist", "Fetch Error: ${response.exception?.message}")
            _isLoadingViewer.value = false
        } catch (e: ApolloException) {
            Log.i("Anilist", "Apollo Exception: ${e.message}")
            _isLoadingViewer.value = false
        }
    }

    fun logout() {
        viewModelScope.launch {
            settingRepository.saveAccessToken("")
        }
    }

}