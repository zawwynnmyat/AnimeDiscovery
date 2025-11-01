package com.zawmyat.anime_discovery.data.pagination

import androidx.compose.runtime.collectAsState
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zawmyat.anime_discovery.TrendingAnimeHomeQuery
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import org.koin.compose.getKoin

class TrendingAnimePagingSource(
    val mainRepository: MainRepository
) : PagingSource<Int, TrendingAnimeHomeQuery.Medium>() {

    override fun getRefreshKey(state: PagingState<Int, TrendingAnimeHomeQuery.Medium>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TrendingAnimeHomeQuery.Medium> {
        return try {
            val page = params.key ?: 1
            val response = mainRepository.getTrendingAnimeHome(page = page, perPage = 10).first()

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
