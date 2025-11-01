package com.zawmyat.anime_discovery.domain.auth

import kotlinx.coroutines.flow.Flow

interface AnilistRepo {

    suspend fun getAccessToken(clientId: String, clientSecret: String, code: String) : Flow<Response<AccessToken>>

}