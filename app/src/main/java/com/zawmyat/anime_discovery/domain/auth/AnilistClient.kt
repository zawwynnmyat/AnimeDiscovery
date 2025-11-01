package com.zawmyat.anime_discovery.domain.auth

import com.zawmyat.anime_discovery.data.auth.Constants
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface AnilistClient {

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun getAccessToken(
        @Field("grant_type") grant_type : String= "authorization_code",
        @Field("client_id") client_id : String,
        @Field("client_secret") client_secret : String,
        @Field("redirect_uri") redirect_uri : String,
        @Field("code") code: String,
    ) : Response<AccessToken>

}