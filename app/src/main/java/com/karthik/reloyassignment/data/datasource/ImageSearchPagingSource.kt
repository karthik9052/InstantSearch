package com.karthik.reloyassignment.data.datasource


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.karthik.reloyassignment.data.response.Hit
import com.karthik.reloyassignment.data.APIService

class ImageSearchPagingSource(private val apiService: APIService, var searchText: String): PagingSource<Int, Hit>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Hit> {
        return try {
            val currentLoadingPageKey: Int = params.key ?: FIRST_PAGE_INDEX
            val response = apiService.getDataFromAPI(searchText,currentLoadingPageKey)
            val responseData = mutableListOf<Hit>()
            val data = response.body()?.hits ?: emptyList()
            responseData.addAll(data)

            val prevKey = if (currentLoadingPageKey == 1) null else currentLoadingPageKey - 1
            val nextKey = if (data.isEmpty()) null else currentLoadingPageKey.plus(1)

            return LoadResult.Page(
                data = responseData,
                prevKey = prevKey,
                nextKey = nextKey
            )
        }
        catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Hit>): Int? {
        return state.anchorPosition
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }
}