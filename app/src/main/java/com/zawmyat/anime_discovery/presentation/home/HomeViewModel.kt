package com.zawmyat.anime_discovery.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.apollographql.apollo.api.Optional
import com.zawmyat.anime_discovery.CharactersQuery
import com.zawmyat.anime_discovery.MediaQuery
import com.zawmyat.anime_discovery.ReviewDetailsQuery
import com.zawmyat.anime_discovery.ReviewsQuery
import com.zawmyat.anime_discovery.StaffsQuery
import com.zawmyat.anime_discovery.StudiosQuery
import com.zawmyat.anime_discovery.TrendingAnimeHomeQuery
import com.zawmyat.anime_discovery.TrendingMangaHomeQuery
import com.zawmyat.anime_discovery.data.auth.Token
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import com.zawmyat.anime_discovery.domain.repository.SettingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MainRepository,
) : ViewModel() {

    private val _genreCollection = MutableStateFlow<List<String?>>(emptyList())
    val genreCollection: StateFlow<List<String?>> get() = _genreCollection

    //Trending Anime Home Page
    private val _loadingTrendingAnimeHome = MutableStateFlow(false)
    val loadingTrendingAnimeHome: StateFlow<Boolean> get() = _loadingTrendingAnimeHome

    private val _trendingAnimeHome = MutableStateFlow<List<TrendingAnimeHomeQuery.Medium?>?>(emptyList())
    val trendingAnimeHome: StateFlow<List<TrendingAnimeHomeQuery.Medium?>?> get() = _trendingAnimeHome

    //Trending Manga Home Page
    private val _loadingTrendingMangaHome = MutableStateFlow(false)
    val loadingTrendingMangaHome: StateFlow<Boolean> get() = _loadingTrendingMangaHome

    private val _trendingMangaHome = MutableStateFlow<List<TrendingMangaHomeQuery.Medium?>?>(emptyList())
    val trendingMangaHome: StateFlow<List<TrendingMangaHomeQuery.Medium?>?> get() = _trendingMangaHome



    init {
        getTrendingAnimesHome()
        getTrendingMangaHome()

    }


    fun getTrendingAnimesHome() {
        viewModelScope.launch {
            repository
                .getTrendingAnimeHome(
                    page = 1,
                    perPage = 10
                )
                .onStart {
                    _loadingTrendingAnimeHome.value = true
                }.catch {
                    _loadingTrendingAnimeHome.value = false
                    Log.e("error_loading", it.message.toString())
                }.collect {
                    tr ->
                    _trendingAnimeHome.value = tr
                    _loadingTrendingAnimeHome.value = false
                }
        }
    }

    fun getTrendingMangaHome() {
        viewModelScope.launch {
            repository
                .getTrendingMangaHome(
                    page = 1,
                    perPage = 10
                )
                .onStart {
                    _loadingTrendingMangaHome.value = true
                }.catch {
                    _loadingTrendingMangaHome.value = false
                    Log.e("error_loading", it.message.toString())
                }.collect {
                        tr ->
                    _trendingMangaHome.value = tr
                    _loadingTrendingMangaHome.value = false
                }
        }
    }


}