package com.zawmyat.anime_discovery.data.auth

import com.zawmyat.anime_discovery.domain.auth.AccessToken
import com.zawmyat.anime_discovery.domain.auth.AnilistClient
import com.zawmyat.anime_discovery.domain.auth.AnilistRepo
import com.zawmyat.anime_discovery.domain.auth.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class AniListRepoImpl(
    private val anilistCilent: AnilistClient
) : AnilistRepo {

    override suspend fun getAccessToken(
        clientId: String,
        clientSecret: String,
        code: String
    ): Flow<Response<AccessToken>> = flow {
        emit(Response.Loading)
        val accessTokenOperation = anilistCilent.getAccessToken(
            client_id = clientId,
            client_secret = clientSecret,
            redirect_uri = Constants.REDIRECT_URL,
            code = code
        )

        if(accessTokenOperation.isSuccessful) {
            emit(Response.Success(accessTokenOperation.body()!!))
        } else {
            emit(Response.Failure(Exception("Failed to get access token!")))
        }
    }.catch {
        cause: Throwable ->
        emit(Response.Failure(Exception("Error! Please try again")))
    }

}