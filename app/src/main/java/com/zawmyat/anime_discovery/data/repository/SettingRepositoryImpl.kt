package com.zawmyat.anime_discovery.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.zawmyat.anime_discovery.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private const val storeName = "app_preferences"
const val THEME_KEY = "app_theme"
const val ONBOARDING_KEY = "onboarding"
const val ACCESS_TOKEN_KEY = "accessToken"

private val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = storeName)

class SettingRepositoryImpl(
    private val context : Context
) : SettingRepository {

    override suspend fun saveTheme(value: String) {
        val storeKey = stringPreferencesKey(THEME_KEY)
        context.dataStore.edit {
            preferences ->
            preferences[storeKey] = value
        }
    }

    override fun getTheme(): Flow<String> {
        val storeKey = stringPreferencesKey(THEME_KEY)
        return context.dataStore.data.map {
            preferences ->
            preferences[storeKey] ?: ""
        }
    }

    override suspend fun saveOnboardStatus(value: Boolean) {
        val storeKey = booleanPreferencesKey(ONBOARDING_KEY)
        context.dataStore.edit {
            preferences ->
            preferences[storeKey] = value
        }
    }

    override fun getOnboardStatus(): Flow<Boolean> {
        val storeKey = booleanPreferencesKey(ONBOARDING_KEY)
        return context.dataStore.data.map {
            preferences ->
            preferences[storeKey] ?: false
        }
    }

    override suspend fun saveAccessToken(value: String) {
        val storeKey = stringPreferencesKey(ACCESS_TOKEN_KEY)
        context.dataStore.edit {
            preferences ->
            preferences[storeKey] = value
        }
    }

    override fun getAccessToken(): Flow<String> {
        val storeKey = stringPreferencesKey(ACCESS_TOKEN_KEY)
        return context.dataStore.data.map {
            preferences ->
            preferences[storeKey] ?: ""
        }
    }

}