package com.zawmyat.anime_discovery.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.apollographql.apollo.api.Optional
import com.zawmyat.anime_discovery.CharactersQuery
import com.zawmyat.anime_discovery.SeasonalMediaQuery
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import com.zawmyat.anime_discovery.type.MediaSeason
import com.zawmyat.anime_discovery.type.MediaType
import kotlinx.coroutines.flow.first

class SeasonalMediaPagingSource(
    val mainRepository: MainRepository,
    val season: MediaSeason,
    val type: MediaType,
    val currentYear: Int
) : PagingSource<Int, SeasonalMediaQuery.Medium>() {

    override fun getRefreshKey(state: PagingState<Int, SeasonalMediaQuery.Medium>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SeasonalMediaQuery.Medium> {
        return try {
            val page = params.key ?: 1
            val response = mainRepository.getSeasonalMedia(
                season = season,
                type = type,
                currentYear = currentYear,
                perPage = 10,
                page = page,
            ).first()

            val data = response?.filterNotNull() ?: emptyList()

            LoadResult.Page(
                data = data,
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (response.isNullOrEmpty()) null else
                    page.plus(1),
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}
