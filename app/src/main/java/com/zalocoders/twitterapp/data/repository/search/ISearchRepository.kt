package com.zalocoders.twitterapp.data.repository.search

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.zalocoders.twitterapp.data.model.Tweet

interface ISearchRepository{
	suspend fun searchTweet(query:String): LiveData<PagingData<Tweet>>
}