package com.zalocoders.twitterapp.data.repository.search

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zalocoders.twitterapp.data.api.ApiService
import com.zalocoders.twitterapp.data.db.entities.RecentTweetEntity
import com.zalocoders.twitterapp.data.db.tweet.TweetsDao
import com.zalocoders.twitterapp.data.model.Tweet
import java.io.IOException
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

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
	
	override suspend fun search(query: String) = flow{
		emit(apiService.searchTweets(query))
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
		val position = params.key ?: 1
		
		return try  {
			val response = apiService.searchTweets(query)
			
			val nextKey = if (response.data.isEmpty()) {
				null
			} else {
				// initial load size = 3 * NETWORK_PAGE_SIZE
				// ensure we're not requesting duplicating items, at the 2nd request
				position + (params.loadSize / 3)
			}
			
			LoadResult.Page(
					data = response.data,
					prevKey = if (position == 1) null else position - 1,
					nextKey = nextKey
			)
		}catch (exception: IOException) {
			return LoadResult.Error(exception)
		} catch (exception: HttpException) {
			return LoadResult.Error(exception)
		}
	}
	
}
