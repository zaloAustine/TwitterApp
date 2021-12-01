package com.zalocoders.twitterapp.data.repository.search

import androidx.paging.PagingData
import com.zalocoders.twitterapp.data.model.SearchResponse
import com.zalocoders.twitterapp.data.model.Tweet
import kotlinx.coroutines.flow.Flow

interface ISearchRepository{
	 fun  searchTweet(query:String): Flow<PagingData<Tweet>>
	 suspend fun search(query: String):Flow<SearchResponse>
}