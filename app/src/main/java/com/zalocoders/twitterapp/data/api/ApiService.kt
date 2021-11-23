package com.zalocoders.twitterapp.data.api

import com.zalocoders.twitterapp.data.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
	@GET("recent?")
	fun searchTweets(@Query("query") query: String):SearchResponse
	
	
}