package com.zawmyat.anime_discovery.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zawmyat.anime_discovery.StatusFilterQuery
import com.zawmyat.anime_discovery.TagMatchQuery
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import com.zawmyat.anime_discovery.type.MediaStatus
import com.zawmyat.anime_discovery.type.MediaType
import kotlinx.coroutines.flow.first

class StatusFilterMediaPagingSource(
    val mainRepository: MainRepository,
    val status: MediaStatus,
    val type: MediaType
) : PagingSource<Int, StatusFilterQuery.Medium>() {

    override fun getRefreshKey(state: PagingState<Int, StatusFilterQuery.Medium>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StatusFilterQuery.Medium> {
        return try {
            val page = params.key ?: 1
            val response = mainRepository.getStatusFilteredMedia(status = status, type = type, page = page, perPage = 10).first()

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
