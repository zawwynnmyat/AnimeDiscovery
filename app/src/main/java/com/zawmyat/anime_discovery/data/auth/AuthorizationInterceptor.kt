package com.zawmyat.anime_discovery.data.auth

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(
    private val token: Token
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .apply {
                token.accessToken?.let {
                    if(it.isNotEmpty()) {
                        addHeader("Authorization", "Bearer $it")
                    }
                }
            }
            .build()

        return chain.proceed(request)
    }

}