package com.zalocoders.twitterapp.data.repository.search

import androidx.paging.PagingData
import com.zalocoders.twitterapp.data.db.entities.RecentTweetEntity
import com.zalocoders.twitterapp.data.model.Tweet
import kotlinx.coroutines.flow.Flow

interface ISearchRepository{
	 fun  searchTweet(query:String): Flow<PagingData<Tweet>>
	suspend fun deleteTweet(id:String):Int
	suspend fun insertTweet(tweet: RecentTweetEntity):Long
}