package com.zawmyat.anime_discovery.domain.repository

import androidx.paging.PagingData
import com.zawmyat.anime_discovery.CharacterDetailsQuery
import com.zawmyat.anime_discovery.CharacterMediaListQuery
import com.zawmyat.anime_discovery.CharactersQuery
import com.zawmyat.anime_discovery.MediaCharacterListQuery
import com.zawmyat.anime_discovery.MediaQuery
import com.zawmyat.anime_discovery.MediaStaffListQuery
import com.zawmyat.anime_discovery.ReviewDetailsQuery
import com.zawmyat.anime_discovery.ReviewsQuery
import com.zawmyat.anime_discovery.SearchCharactersQuery
import com.zawmyat.anime_discovery.SearchMediaQuery
import com.zawmyat.anime_discovery.SearchStaffsQuery
import com.zawmyat.anime_discovery.SeasonalMediaQuery
import com.zawmyat.anime_discovery.SortMediaQuery
import com.zawmyat.anime_discovery.StaffCharactersListQuery
import com.zawmyat.anime_discovery.StaffDetailQuery
import com.zawmyat.anime_discovery.StaffMediasListQuery
import com.zawmyat.anime_discovery.StaffsQuery
import com.zawmyat.anime_discovery.StatusFilterQuery
import com.zawmyat.anime_discovery.StudioDetailQuery
import com.zawmyat.anime_discovery.StudiosQuery
import com.zawmyat.anime_discovery.TagMatchQuery
import com.zawmyat.anime_discovery.TrendingAnimeHomeQuery
import com.zawmyat.anime_discovery.TrendingMangaHomeQuery
import com.zawmyat.anime_discovery.type.MediaSeason
import com.zawmyat.anime_discovery.type.MediaSort
import com.zawmyat.anime_discovery.type.MediaStatus
import com.zawmyat.anime_discovery.type.MediaType

import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getGenreCollection() : Flow<List<String?>>

    fun getTrendingAnimeHome(page: Int?, perPage: Int?) : Flow<List<TrendingAnimeHomeQuery.Medium?>?>
    fun getAllTrendingAnime() : Flow<PagingData<TrendingAnimeHomeQuery.Medium>>

    fun getTrendingMangaHome(page: Int?, perPage: Int?) : Flow<List<TrendingMangaHomeQuery.Medium?>?>
    fun getAllTrendingManga() : Flow<PagingData<TrendingMangaHomeQuery.Medium>>

    fun fetchAllReviews(page: Int?, perPage: Int?) : Flow<List<ReviewsQuery.Review?>?>
    fun getAllReviews() : Flow<PagingData<ReviewsQuery.Review>>

    fun fetchAllCharacters(page: Int?, perPage: Int?) : Flow<List<CharactersQuery.Character?>?>
    fun getAllCharacters() : Flow<PagingData<CharactersQuery.Character>>

    fun fetchAllStaffs(page: Int?, perPage: Int?) : Flow<List<StaffsQuery.Staff?>?>
    fun getAllStaffs() : Flow<PagingData<StaffsQuery.Staff>>

    fun fetchAllStudios(page: Int?, perPage: Int?) : Flow<List<StudiosQuery.Studio?>?>
    fun getAllStudios() : Flow<PagingData<StudiosQuery.Studio>>

    fun getReviewDetails(id: Int?) : Flow<ReviewDetailsQuery.Review?>

    fun getMediaDetails(id: Int?) : Flow<MediaQuery.Media?>

    fun getCharacterDetails(id: Int?) : Flow<CharacterDetailsQuery.Character?>

    fun getStudioDetails(id: Int?) : Flow<StudioDetailQuery.Studio?>

    fun getStaffDetails(id: Int?) : Flow<StaffDetailQuery.Staff?>

    fun getSeasonalMedia(season: MediaSeason, type: MediaType, currentYear: Int, page: Int?, perPage: Int?) : Flow<List<SeasonalMediaQuery.Medium?>?>
    fun getAllPaginatedSeasonalMedia(season: MediaSeason, type: MediaType, currentYear: Int) : Flow<PagingData<SeasonalMediaQuery.Medium>>

    fun getTagMatchMedia(tag: String, page: Int?, perPage: Int?) : Flow<List<TagMatchQuery.Medium?>?>
    fun getPaginatedTagMatchMedia(tag: String) : Flow<PagingData<TagMatchQuery.Medium>>

    fun getStatusFilteredMedia(status: MediaStatus, type: MediaType, page: Int?, perPage: Int?) : Flow<List<StatusFilterQuery.Medium?>?>
    fun getPaginatedStatusFilteredMedia(status: MediaStatus, type: MediaType) : Flow<PagingData<StatusFilterQuery.Medium>>

    fun getSortedMedia(sort: MediaSort, type: MediaType, page: Int?, perPage: Int?) : Flow<List<SortMediaQuery.Medium?>?>
    fun getPaginatedSortedMedia(sort: MediaSort, type: MediaType) : Flow<PagingData<SortMediaQuery.Medium>>

    fun getMediaStaffList(mediaId: Int, page: Int?, perPage: Int?) : Flow<List<MediaStaffListQuery.Edge?>?>
    fun getPaginatedStaffList(mediaId: Int) : Flow<PagingData<MediaStaffListQuery.Edge>>

    fun getMediaCharacterList(mediaId: Int, page: Int?, perPage: Int?) : Flow<List<MediaCharacterListQuery.Edge?>?>
    fun getPaginatedCharacterList(mediaId: Int) : Flow<PagingData<MediaCharacterListQuery.Edge>>

    fun getStaffCharactersList(staffId: Int, page: Int?, perPage: Int?) : Flow<List<StaffCharactersListQuery.Edge?>?>
    fun getPaginatedStaffCharactersList(staffId: Int) : Flow<PagingData<StaffCharactersListQuery.Edge>>

    fun getStaffMediasList(staffId: Int, page: Int?, perPage: Int?) : Flow<List<StaffMediasListQuery.Node?>?>
    fun getPaginatedStaffMediasList(staffId: Int) : Flow<PagingData<StaffMediasListQuery.Node>>

    fun getCharacterMediaList(characterId: Int, page: Int?, perPage: Int?) : Flow<List<CharacterMediaListQuery.Edge?>?>
    fun getPaginatedCharacterMediaList(characterId: Int) : Flow<PagingData<CharacterMediaListQuery.Edge>>

    fun getSearchedMedia(page: Int?, perPage: Int?, search: String, mediaType: MediaType) : Flow<List<SearchMediaQuery.Medium?>?>
    fun getAllSearchedMedia(search: String, mediaType: MediaType) : Flow<PagingData<SearchMediaQuery.Medium>>

    fun getSearchedCharacters(page: Int?, perPage: Int?, search: String) : Flow<List<SearchCharactersQuery.Character?>?>
    fun getAllSearchedCharacters(search: String) : Flow<PagingData<SearchCharactersQuery.Character>>

    fun getSearchedStaffs(page: Int?, perPage: Int?, search: String) : Flow<List<SearchStaffsQuery.Staff?>?>
    fun getAllSearchedStaffs(search: String) : Flow<PagingData<SearchStaffsQuery.Staff>>

}