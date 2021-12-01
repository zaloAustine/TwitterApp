package com.zalocoders.twitterapp.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zalocoders.twitterapp.data.model.Tweet

@Entity(tableName = "recent_tweets")
data class RecentTweetEntity(
		@PrimaryKey(autoGenerate = false)
		val tweet_id: String,
		val tweet_desc: String,
		)

fun Tweet.toRecentTweetEntity():RecentTweetEntity{
	return RecentTweetEntity(
			tweet_id = this.id,
			tweet_desc = this.text
	)
}