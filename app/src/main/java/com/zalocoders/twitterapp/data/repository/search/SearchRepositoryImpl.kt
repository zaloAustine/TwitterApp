package com.zalocoders.twitterapp.data.repository.search

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.liveData
import com.zalocoders.twitterapp.data.api.ApiService
import com.zalocoders.twitterapp.data.model.Tweet
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
		private val apiService: ApiService
):ISearchRepository {
	
	override suspend fun searchTweet(query:String):LiveData<PagingData<Tweet>> {
		return Pager(PagingConfig(pageSize = 25)) {
			SearchDataSource(apiService,query)
		}.liveData
	}
}

class SearchDataSource(
		private val apiService: ApiService,
		private val query: String
) : PagingSource<Int, Tweet>() {
	private var totalPages = 1
	
	override fun getRefreshKey(state: PagingState<Int, Tweet>): Int? {
		// We need to get the previous key (or next key if previous is null) of the page
		// that was closest to the most recently accessed index.
		// Anchor position is the most recently accessed index
		return null
	}
	
	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Tweet> {
		val position = params.key ?: 1
		
		return try {
			val response = apiService.searchTweets(query)
			totalPages = response.meta.result_count
			
			val nextKey = if (response.data.isEmpty()) {
				null
			} else {
				// initial load size = 3 * NETWORK_PAGE_SIZE
				// ensure we're not requesting duplicating items, at the 2nd request
				position + (params.loadSize / response.meta.result_count)
			}
			
			LoadResult.Page(
					data = response.data,
					prevKey =null,
					nextKey = nextKey
			)
		} catch (e: Exception) {
			return LoadResult.Error(e)
		}
	}
	
}
