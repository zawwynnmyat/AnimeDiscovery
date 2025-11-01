package com.zawmyat.anime_discovery.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zawmyat.anime_discovery.SearchCharactersQuery
import com.zawmyat.anime_discovery.SearchStaffsQuery
import com.zawmyat.anime_discovery.domain.repository.MainRepository
import kotlinx.coroutines.flow.first

class SearchStaffsPagingSource(
    val mainRepository: MainRepository,
    val search: String,
) : PagingSource<Int, SearchStaffsQuery.Staff>() {

    override fun getRefreshKey(state: PagingState<Int, SearchStaffsQuery.Staff>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchStaffsQuery.Staff> {
        return try {
            val page = params.key ?: 1
            val response = mainRepository.getSearchedStaffs(
                page = page,
                perPage = 10,
                search = search,
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
