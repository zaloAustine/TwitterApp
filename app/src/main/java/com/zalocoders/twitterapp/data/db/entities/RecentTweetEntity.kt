package com.zalocoders.twitterapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_tweets")
data class RecentTweetEntity(
		@PrimaryKey(autoGenerate = false)
		val tweet_id: String,
		val tweet_desc: String,
		)