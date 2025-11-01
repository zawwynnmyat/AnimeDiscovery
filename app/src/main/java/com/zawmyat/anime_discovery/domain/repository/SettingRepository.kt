package com.zawmyat.anime_discovery.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingRepository {

    suspend fun saveTheme(value: String)
    fun getTheme() : Flow<String>

    suspend fun saveOnboardStatus(value: Boolean)
    fun getOnboardStatus() : Flow<Boolean>

    suspend fun saveAccessToken(value: String)
    fun getAccessToken() : Flow<String>


}