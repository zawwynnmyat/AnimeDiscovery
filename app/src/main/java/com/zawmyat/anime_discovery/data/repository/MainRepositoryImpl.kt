package com.zawmyat.anime_discovery.data.repository

import android.graphics.pdf.PdfDocument.Page
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.zawmyat.anime_discovery.CharacterDetailsQuery
import com.zawmyat.anime_discovery.CharacterMediaListQuery
import com.zawmyat.anime_discovery.CharactersQuery
import com.zawmyat.anime_discovery.GenreQuery
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
import com.zawmyat.anime_discovery.data.pagination.CharacterMediaListPagingSource
import com.zawmyat.anime_discovery.data.pagination.CharactersPagingSource
import com.zawmyat.anime_discovery.data.pagination.MediaCharacterListPagingSource
import com.zawmyat.anime_discovery.data.pagination.MediaStaffListPagingSource
import com.zawmyat.anime_discovery.data.pagination.ReviewsPagingSource
import com.zawmyat.anime_discovery.data.pagination.SearchCharactersPagingSource
import com.zawmyat.anime_discovery.data.pagination.SearchMediaPagingSource
import com.zawmyat.anime_discovery.data.pagination.SearchStaffsPagingSource
import com.zawmyat.anime_discovery.data.pagination.SeasonalMediaPagingSource
import com.zawmyat.anime_discovery.data.pagination.SortedMediaPagingSource
import com.zawmyat.anime_discovery.data.pagination.StaffCharactersListPagingSource
import com.zawmyat.anime_discovery.data.pagination.StaffMediasListPagingSource
import com.zawmyat.anime_discovery.data.pagination.StaffsPagingSource
import com.zawmyat.anime_discovery.data.pagination.StatusFilterMediaPagingSource
import com.zawmyat.anime_discovery.data.pagination.StudiosPagingSource
import com.zawmyat.anime_discovery.data.pagination.TagMatchedPagingSource
import com.zawmyat.anime_discovery.data.pagination.TrendingAnimePagingSource
import com.zawmyat.anime_discovery.data.pagination.TrendingMangaPagingSource
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import com.zawmyat.anime_discovery.type.MediaSeason
import com.zawmyat.anime_discovery.type.MediaSort
import com.zawmyat.anime_discovery.type.MediaStatus
import com.zawmyat.anime_discovery.type.MediaType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MainRepositoryImpl(
    private val apolloClient: ApolloClient,
) : MainRepository {

    override fun getGenreCollection(): Flow<List<String?>> {
        val genreCollection = apolloClient
            .query(GenreQuery())
            .toFlow()
            .map {
                response ->
                response.data?.GenreCollection ?: emptyList()
            }

        return genreCollection
    }

    override fun getTrendingAnimeHome(page: Int?, perPage: Int?): Flow<List<TrendingAnimeHomeQuery.Medium?>?> {
        val trendingAnimeHome = apolloClient
            .query(
                TrendingAnimeHomeQuery(
                    page = Optional.present(page),
                    perPage = Optional.present(perPage)
                )
            )
            .toFlow()
            .map {
                response ->
                response.data?.Page?.media
            }

        return trendingAnimeHome
    }

    override fun getAllTrendingAnime(): Flow<PagingData<TrendingAnimeHomeQuery.Medium>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = {
                TrendingAnimePagingSource(mainRepository = this)
            }
        ).flow
    }

    override fun getTrendingMangaHome(
        page: Int?,
        perPage: Int?
    ): Flow<List<TrendingMangaHomeQuery.Medium?>?> {

        val trendingMangaHome = apolloClient
            .query(
                TrendingMangaHomeQuery(
                    page = Optional.present(page),
                    perPage = Optional.present(perPage)
                )
            )
            .toFlow()
            .map {
                    response ->
                response.data?.Page?.media?.filterNotNull() ?: emptyList()
            }

        return trendingMangaHome

    }

    override fun getAllTrendingManga(): Flow<PagingData<TrendingMangaHomeQuery.Medium>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                TrendingMangaPagingSource(mainRepository = this)
            }
        ).flow
    }

    //Reviews
    override fun fetchAllReviews(page: Int?, perPage: Int?): Flow<List<ReviewsQuery.Review?>?> {
       return apolloClient
           .query(
               ReviewsQuery(
                   page = Optional.present(page),
                   perPage = Optional.present(perPage)
               )
           )
           .toFlow()
           .map {
               response ->
               response.data?.Page?.reviews
           }
    }

    override fun getAllReviews(): Flow<PagingData<ReviewsQuery.Review>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = {
                ReviewsPagingSource(mainRepository = this)
            }
        ).flow
    }

    //Characters
    override fun fetchAllCharacters(
        page: Int?,
        perPage: Int?
    ): Flow<List<CharactersQuery.Character?>?> {
        return apolloClient.query(
            CharactersQuery(
                page = Optional.present(page),
                perPage = Optional.present(perPage)
            )
        ).toFlow()
            .map {
                character ->
                character.data?.Page?.characters
            }
    }

    override fun getAllCharacters(): Flow<PagingData<CharactersQuery.Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = {
                CharactersPagingSource(mainRepository = this)
            }
        ).flow
    }

    //Staffs
    override fun fetchAllStaffs(page: Int?, perPage: Int?): Flow<List<StaffsQuery.Staff?>?> {
        return apolloClient.query(
            StaffsQuery(
                page = Optional.present(page),
                perPage = Optional.present(perPage)
            )
        ).toFlow()
            .map {
                staffs ->
                staffs.data?.Page?.staff
            }
    }

    override fun getAllStaffs(): Flow<PagingData<StaffsQuery.Staff>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = {
                StaffsPagingSource(mainRepository = this)
            }
        ).flow
    }


    //Studios
    override fun fetchAllStudios(page: Int?, perPage: Int?): Flow<List<StudiosQuery.Studio?>?> {
        return apolloClient.query(
            StudiosQuery(
                page = Optional.present(page),
                perPage = Optional.present(perPage)
            )
        ).toFlow()
            .map {
                studios ->
                studios.data?.Page?.studios
            }
    }

    override fun getAllStudios(): Flow<PagingData<StudiosQuery.Studio>> {
        return Pager(
           config = PagingConfig(
               pageSize = 10
           ),
            pagingSourceFactory = {
                StudiosPagingSource(
                    mainRepository = this
                )
            }
        ).flow
    }

    //Review Details
    override fun getReviewDetails(id: Int?): Flow<ReviewDetailsQuery.Review?> {
        return apolloClient.query(
            ReviewDetailsQuery(
                reviewId = Optional.present(id)
            )
        ).toFlow()
            .map {
                review ->
                review.data?.Review
            }
    }

    override fun getMediaDetails(id: Int?): Flow<MediaQuery.Media?> {
        return apolloClient.query(
            MediaQuery(
                mediaId = Optional.present(id)
            )
        ).toFlow()
            .map {
                media ->
                media.data?.Media
            }
    }

    override fun getCharacterDetails(id: Int?): Flow<CharacterDetailsQuery.Character?> {
        return apolloClient.query(
            CharacterDetailsQuery(
                characterId = Optional.present(id)
            )
        ).toFlow()
            .map {
                character ->
                character.data?.Character
            }
    }

    override fun getStudioDetails(id: Int?): Flow<StudioDetailQuery.Studio?> {
        return apolloClient.query(
            StudioDetailQuery(
                studioId = Optional.present(id)
            )
        ).toFlow()
            .map {
                studio ->
                studio.data?.Studio
            }
    }

    override fun getStaffDetails(id: Int?): Flow<StaffDetailQuery.Staff?> {
        return apolloClient.query(
            StaffDetailQuery(
                staffId = Optional.present(id)
            )
        ).toFlow()
            .map {
                staff ->
                staff.data?.Staff
            }
    }

    override fun getSeasonalMedia(
        season: MediaSeason,
        type: MediaType,
        currentYear: Int,
        page: Int?,
        perPage: Int?
    ): Flow<List<SeasonalMediaQuery.Medium?>?> {
        return apolloClient.query(
            SeasonalMediaQuery(
               season = Optional.present(season),
                type = Optional.present(type),
                seasonYear = Optional.present(currentYear),
                page = Optional.present(page),
                perPage = Optional.present(perPage)
            )
        ).toFlow()
            .map {
                seasonalMedia ->
                seasonalMedia.data?.Page?.media
            }
    }

    override fun getAllPaginatedSeasonalMedia(
        season: MediaSeason,
        type: MediaType,
        currentYear: Int,
    ): Flow<PagingData<SeasonalMediaQuery.Medium>> {

        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                SeasonalMediaPagingSource(
                    mainRepository = this,
                    season = season,
                    type = type,
                    currentYear = currentYear
                )
            }
        ).flow
    }

    override fun getTagMatchMedia(
        tag: String,
        page: Int?,
        perPage: Int?
    ): Flow<List<TagMatchQuery.Medium?>?> {
        return apolloClient.query(
            TagMatchQuery(
                tag = Optional.present(tag),
                page = Optional.present(page),
                perPage = Optional.present(page)
            )
        ).toFlow()
            .map {
                tagMatchedMedia->
                tagMatchedMedia.data?.Page?.media
            }
    }

    override fun getPaginatedTagMatchMedia(tag: String)
    : Flow<PagingData<TagMatchQuery.Medium>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = {
                TagMatchedPagingSource(mainRepository = this, tag = tag)
            }
        ).flow
    }

    override fun getStatusFilteredMedia(
        status: MediaStatus,
        type: MediaType,
        page: Int?,
        perPage: Int?
    ): Flow<List<StatusFilterQuery.Medium?>?> {
        return apolloClient.query(
            StatusFilterQuery(
                statusIn = Optional.present(listOf(status)),
                type = Optional.present(type),
                page = Optional.present(page),
                perPage = Optional.present(perPage)
            )
        ).toFlow()
            .map {
                statusFilteredMedia ->
                statusFilteredMedia.data?.Page?.media
            }
    }

    override fun getPaginatedStatusFilteredMedia(
        status: MediaStatus,
        type: MediaType
    ): Flow<PagingData<StatusFilterQuery.Medium>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
               StatusFilterMediaPagingSource(
                   mainRepository = this,
                   status = status,
                   type = type
               )
            }
        ).flow
    }

    override fun getSortedMedia(
        sort: MediaSort,
        type: MediaType,
        page: Int?,
        perPage: Int?
    ): Flow<List<SortMediaQuery.Medium?>?> {
        return apolloClient.query(
            SortMediaQuery(
                page = Optional.present(page),
                perPage = Optional.present(perPage),
                sort = Optional.present(listOf(sort)),
                type = Optional.present(type)
            )
        ).toFlow()
            .map {
                sortedMedia ->
                sortedMedia.data?.Page?.media
            }
    }

    override fun getPaginatedSortedMedia(
        sort: MediaSort,
        type: MediaType
    ): Flow<PagingData<SortMediaQuery.Medium>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                SortedMediaPagingSource(
                    sort = sort,
                    type = type,
                    mainRepository = this
                )
            }
        ).flow
    }

    //Media's Staff List
    override fun getMediaStaffList(
        mediaId: Int,
        page: Int?,
        perPage: Int?
    ): Flow<List<MediaStaffListQuery.Edge?>?> {
        return apolloClient.query(
            MediaStaffListQuery(
                mediaId = Optional.present(mediaId),
                page = Optional.present(page),
                perPage = Optional.present(perPage)
            )
        ).toFlow()
            .map {
                mediaStaff ->
                mediaStaff.data?.Media?.staff?.edges
            }
    }

    override fun getPaginatedStaffList(mediaId: Int): Flow<PagingData<MediaStaffListQuery.Edge>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                MediaStaffListPagingSource(
                    mediaId = mediaId,
                    mainRepository = this
                )
            }
        ).flow
    }

    override fun getMediaCharacterList(
        mediaId: Int,
        page: Int?,
        perPage: Int?
    ): Flow<List<MediaCharacterListQuery.Edge?>?> {
        return apolloClient.query(
            MediaCharacterListQuery(
                mediaId = Optional.present(mediaId),
                page = Optional.present(page),
                perPage = Optional.present(perPage)
            )
        ).toFlow()
            .map {
                mediaCharacters ->
                mediaCharacters.data?.Media?.characters?.edges
            }
    }

    override fun getPaginatedCharacterList(mediaId: Int): Flow<PagingData<MediaCharacterListQuery.Edge>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                MediaCharacterListPagingSource(
                    mediaId = mediaId,
                    mainRepository = this
                )
            }
        ).flow
    }

    override fun getStaffCharactersList(
        staffId: Int,
        page: Int?,
        perPage: Int?
    ): Flow<List<StaffCharactersListQuery.Edge?>?> {
        return apolloClient.query(
            StaffCharactersListQuery(
                staffId = Optional.present(staffId),
                page = Optional.present(page),
                perPage = Optional.present(perPage)
            )
        ).toFlow()
            .map {
                staffCharacters ->
                staffCharacters.data?.Staff?.characters?.edges
            }
    }

    override fun getPaginatedStaffCharactersList(staffId: Int): Flow<PagingData<StaffCharactersListQuery.Edge>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                StaffCharactersListPagingSource(
                    staffId = staffId,
                    mainRepository = this
                )
            }
        ).flow
    }

    override fun getStaffMediasList(
        staffId: Int,
        page: Int?,
        perPage: Int?
    ): Flow<List<StaffMediasListQuery.Node?>?> {
        return apolloClient.query(
            StaffMediasListQuery(
                staffId = Optional.present(staffId),
                page = Optional.present(page),
                perPage = Optional.present(perPage)
            )
        ).toFlow()
            .map {
                staffMedias ->
                staffMedias.data?.Staff?.staffMedia?.nodes
            }
    }

    override fun getPaginatedStaffMediasList(staffId: Int): Flow<PagingData<StaffMediasListQuery.Node>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                StaffMediasListPagingSource(
                    staffId = staffId,
                    mainRepository = this
                )
            }
        ).flow
    }

    override fun getCharacterMediaList(
        characterId: Int,
        page: Int?,
        perPage: Int?
    ): Flow<List<CharacterMediaListQuery.Edge?>?> {
        return apolloClient.query(
            CharacterMediaListQuery(
                characterId = Optional.present(characterId),
                page = Optional.present(page),
                perPage = Optional.present(perPage)
            )
        ).toFlow()
            .map {
                characterMediaList ->
                characterMediaList.data?.Character?.media?.edges
            }
    }

    override fun getPaginatedCharacterMediaList(characterId: Int): Flow<PagingData<CharacterMediaListQuery.Edge>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                CharacterMediaListPagingSource(
                    characterId = characterId,
                    mainRepository = this
                )
            }
        ).flow
    }

    override fun getSearchedMedia(
        page: Int?,
        perPage: Int?,
        search: String,
        mediaType: MediaType
    ): Flow<List<SearchMediaQuery.Medium?>?> {
        return apolloClient.query(
            SearchMediaQuery(
                search = Optional.present(search),
                type = Optional.present(mediaType),
                page = Optional.present(page),
                perPage = Optional.present(perPage)
            )
        ).toFlow()
            .map {
                searchMediaList ->
                searchMediaList.data?.Page?.media
            }
    }

    override fun getAllSearchedMedia(search: String, mediaType: MediaType): Flow<PagingData<SearchMediaQuery.Medium>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
            ),
            pagingSourceFactory = {
                SearchMediaPagingSource(
                    search = search,
                    mediaType = mediaType,
                    mainRepository = this
                )
            }
        ).flow
    }

    override fun getSearchedCharacters(
        page: Int?,
        perPage: Int?,
        search: String
    ): Flow<List<SearchCharactersQuery.Character?>?> {
        return apolloClient.query(
            SearchCharactersQuery(
                page = Optional.present(page),
                perPage = Optional.present(perPage),
                search = Optional.present(search)
            )
        ).toFlow()
            .map {
                searchCharacterList->
                searchCharacterList.data?.Page?.characters
            }
    }

    override fun getAllSearchedCharacters(search: String): Flow<PagingData<SearchCharactersQuery.Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                SearchCharactersPagingSource(
                    search = search,
                    mainRepository = this
                )
            }
        ).flow
    }

    override fun getSearchedStaffs(
        page: Int?,
        perPage: Int?,
        search: String
    ): Flow<List<SearchStaffsQuery.Staff?>?> {
        return apolloClient.query(
            SearchStaffsQuery(
                page = Optional.present(page),
                perPage = Optional.present(perPage),
                search = Optional.present(search)
            )
        ).toFlow()
            .map {
                searchStaffsList ->
                searchStaffsList.data?.Page?.staff
            }
    }

    override fun getAllSearchedStaffs(search: String): Flow<PagingData<SearchStaffsQuery.Staff>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            pagingSourceFactory = {
                SearchStaffsPagingSource(
                    search = search,
                    mainRepository = this

                )
            }
        ).flow
    }


}