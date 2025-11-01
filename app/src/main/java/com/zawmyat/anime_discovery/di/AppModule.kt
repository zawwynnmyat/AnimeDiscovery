package com.zawmyat.anime_discovery.di

import androidx.collection.LruCache
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.cache.http.ApolloHttpCache
import com.apollographql.apollo.cache.http.DiskLruHttpCache
import com.apollographql.apollo.cache.http.HttpFetchPolicy
import com.apollographql.apollo.cache.http.httpCache
import com.apollographql.apollo.cache.http.httpExpireTimeout
import com.apollographql.apollo.cache.http.httpFetchPolicy
import com.apollographql.apollo.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo.cache.normalized.api.NormalizedCacheFactory
import com.apollographql.apollo.cache.normalized.normalizedCache
import com.apollographql.apollo.network.okHttpClient
import com.zawmyat.anime_discovery.data.DownloaderImpl
import com.zawmyat.anime_discovery.data.auth.AniListRepoImpl
import com.zawmyat.anime_discovery.data.auth.AuthorizationInterceptor
import com.zawmyat.anime_discovery.data.auth.Constants
import com.zawmyat.anime_discovery.data.auth.Token
import com.zawmyat.anime_discovery.data.repository.MainRepositoryImpl
import com.zawmyat.anime_discovery.data.repository.SettingRepositoryImpl
import com.zawmyat.anime_discovery.data.utils.CacheInterceptor
import com.zawmyat.anime_discovery.domain.Downloader
import com.zawmyat.anime_discovery.domain.auth.AnilistClient
import com.zawmyat.anime_discovery.domain.auth.AnilistRepo
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import com.zawmyat.anime_discovery.domain.repository.SettingRepository
import com.zawmyat.anime_discovery.presentation.account.AccountViewModel
import com.zawmyat.anime_discovery.presentation.anime.SeasonalViewModel
import com.zawmyat.anime_discovery.presentation.characters.AllCharactersViewModel
import com.zawmyat.anime_discovery.presentation.characters.CharacterDetailsViewModel
import com.zawmyat.anime_discovery.presentation.characters.CharacterMediaListViewModel
import com.zawmyat.anime_discovery.presentation.details.AnimeDetailsViewModel
import com.zawmyat.anime_discovery.presentation.details.MediaCharacterListViewModel
import com.zawmyat.anime_discovery.presentation.details.MediaStaffListViewModel
import com.zawmyat.anime_discovery.presentation.details.TagMatchedViewModel
import com.zawmyat.anime_discovery.presentation.home.HomeViewModel
import com.zawmyat.anime_discovery.presentation.manga.SortMediaViewModel
import com.zawmyat.anime_discovery.presentation.manga.StatusFilteredMediaViewModel
import com.zawmyat.anime_discovery.presentation.photo.DownloadViewModel
import com.zawmyat.anime_discovery.presentation.reviews.AllReviewsViewModel
import com.zawmyat.anime_discovery.presentation.reviews.ReviewViewModel
import com.zawmyat.anime_discovery.presentation.search.SearchCharactersViewModel
import com.zawmyat.anime_discovery.presentation.search.SearchMediaViewModel
import com.zawmyat.anime_discovery.presentation.search.SearchStaffsViewModel
import com.zawmyat.anime_discovery.presentation.setting.SettingViewModel
import com.zawmyat.anime_discovery.presentation.staffs.AllStaffsViewModel
import com.zawmyat.anime_discovery.presentation.staffs.StaffCharactersListViewModel
import com.zawmyat.anime_discovery.presentation.staffs.StaffDetailViewModel
import com.zawmyat.anime_discovery.presentation.staffs.StaffMediasListViewModel
import com.zawmyat.anime_discovery.presentation.studios.AllStudioViewModel
import com.zawmyat.anime_discovery.presentation.studios.StudioDetailsViewModel
import com.zawmyat.anime_discovery.presentation.trending.AllTrendingAnimeViewModel
import com.zawmyat.anime_discovery.presentation.trending.AllTrendingMangaViewModel
import okhttp3.Cache

import org.koin.dsl.module
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

val appModule = module {

    single<OkHttpClient> {
       OkHttpClient.Builder()
           .addInterceptor(get<AuthorizationInterceptor>())
           .build()
    }


    single {
        ApolloClient
            .Builder()
            .okHttpClient(okHttpClient = get())
            .serverUrl("https://graphql.anilist.co")
            .okHttpClient(get())
            .httpCache(
                directory = File(androidContext().cacheDir, "apolloCache"),
                maxSize = 100 * 1024 * 1024
            )
            .httpFetchPolicy(httpFetchPolicy = HttpFetchPolicy.NetworkFirst)
            .build()
    }

    single<Retrofit> {
        val retrofits by lazy {
            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        retrofits
    }

    single<AnilistClient> {
        val api: AnilistClient by lazy {
            get<Retrofit>().create(
                AnilistClient::class.java
            )
        }

        api
    }

    single<AnilistRepo> {
        AniListRepoImpl(anilistCilent = get())
    }

    viewModel {
        AccountViewModel(
            aniListRepo = get<AnilistRepo>(),
            settingRepository = get<SettingRepository>(),
            token = get<Token>(),
            client = get<ApolloClient>()
        )
    }

    single<Token> {
        Token()
    }

    single<AuthorizationInterceptor> {
        AuthorizationInterceptor(token = get<Token>())
    }



    single <MainRepository>{
        MainRepositoryImpl(apolloClient = get())
    }

    single<SettingRepository>{
        SettingRepositoryImpl(context = androidContext())
    }

    viewModel {
        SettingViewModel(get())
    }

    viewModel {
        HomeViewModel(get())
    }

    viewModel {
        ReviewViewModel(get(), get())
    }

    viewModel {
        AnimeDetailsViewModel(get(), get())
    }

    viewModel {
        AllReviewsViewModel(get())
    }

    viewModel {
        CharacterDetailsViewModel(get(), get())
    }

    viewModel {
        StudioDetailsViewModel(get(), get())
    }

    viewModel {
        StaffDetailViewModel(get(), get())
    }

    viewModel {
        AllCharactersViewModel(get())
    }

    viewModel {
        AllStudioViewModel(get())
    }

    viewModel {
        AllStaffsViewModel(get())
    }

    viewModel{
        params ->
        SeasonalViewModel(get(), season = params[0], type = params[1], currentYear = params[2])
    }

    viewModel {
        params ->
        TagMatchedViewModel(get(), tag = params[0])
    }

    viewModel {
        params ->
        SortMediaViewModel(repository = get(), sort = params[0], type = params[1])
    }

    viewModel {
        params ->
        StatusFilteredMediaViewModel(repository = get(), status = params[0], type = params[1])
    }

    viewModel {
        params ->
        MediaStaffListViewModel(get(), mediaId = params[0])
    }

    viewModel {
        params ->
        MediaCharacterListViewModel(get(), mediaId = params[0])
    }

    viewModel {
        params ->
        StaffCharactersListViewModel(get(), staffId = params[0])
    }

    viewModel {
        params ->
        StaffMediasListViewModel(get(), staffId = params[0])
    }

    viewModel {
        params ->
        CharacterMediaListViewModel(get(), characterId = params[0])
    }

    viewModel {
        AllTrendingAnimeViewModel(get())
    }

    viewModel {
        AllTrendingMangaViewModel(get())
    }

    single<Downloader> {
        DownloaderImpl(context = androidContext())
    }

    viewModel{
        DownloadViewModel(get())
    }

    viewModel {
        SearchMediaViewModel(get(), get())
    }

    viewModel {
        SearchCharactersViewModel(get())
    }

    viewModel {
        SearchStaffsViewModel(get())
    }
}