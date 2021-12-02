package com.zalocoders.twitterapp.data.repository.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zalocoders.twitterapp.data.api.ApiService
import com.zalocoders.twitterapp.data.db.entities.RecentTweetEntity
import com.zalocoders.twitterapp.data.db.tweet.TweetsDao
import com.zalocoders.twitterapp.data.model.Tweet
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class SearchRepositoryImpl @Inject constructor(
		private val apiService: ApiService,
		private val tweetsDao: TweetsDao
):ISearchRepository{
	
	override fun searchTweet(query:String): Flow<PagingData<Tweet>> {
		return Pager(
				config = PagingConfig(
						pageSize = 25,
						enablePlaceholders = false
				),
				pagingSourceFactory = { SearchDataSource(apiService, query) }
		).flow
	}
	
	override suspend fun deleteTweet(id:String) = tweetsDao.deleteTweet(id)
	
	override suspend fun insertTweet(tweet: RecentTweetEntity) = tweetsDao.insertTweet(tweet)
	
}

class SearchDataSource(
		private val apiService: ApiService,
		private val query: String
) : PagingSource<Int, Tweet>() {
	
	override fun getRefreshKey(state: PagingState<Int, Tweet>): Int? {
		// We need to get the previous key (or next key if previous is null) of the page
		// that was closest to the most recently accessed index.
		// Anchor position is the most recently accessed index
		return state.anchorPosition?.let { anchorPosition ->
			state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
					?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
		}
	}
	
	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Tweet> {
		
		return try  {
			val position = params.key ?: 1
			
			val response = apiService.searchTweets(query)
			
			val nextKey = if (response.data.isNotEmpty()) position + 1 else null
			
			LoadResult.Page(
					data = response.data,
					prevKey = if (position == 1) null else position - 1,
					nextKey = nextKey
			)
		}catch (exception: Exception) {
			return LoadResult.Error(Throwable("Check your internet"))
		}
	}
}
