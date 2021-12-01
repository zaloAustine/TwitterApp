package com.zalocoders.twitterapp.data.db.tweet

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zalocoders.twitterapp.data.db.entities.RecentTweetEntity


@Dao
interface TweetsDao {
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertTweet(tweet: RecentTweetEntity):Long
	
	@Query("SELECT * FROM recent_tweets")
	fun getAllTweets(): LiveData<List<RecentTweetEntity>>
	
	@Query("DELETE FROM recent_tweets")
	suspend fun deleteAllTweet():Int
	
	@Query("DELETE FROM recent_tweets WHERE tweet_id = :id")
	suspend fun deleteTweet(id:String):Int
}
