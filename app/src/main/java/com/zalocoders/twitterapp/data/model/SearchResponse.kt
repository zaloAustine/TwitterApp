package com.zalocoders.twitterapp.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(
		val data: List<Tweet>,
		val meta: Meta
)