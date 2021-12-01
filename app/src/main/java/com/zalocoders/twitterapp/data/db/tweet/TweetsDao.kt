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
	fun insertTweet(tweet: RecentTweetEntity):LiveData<Long>
	
	@Query("SELECT * FROM recent_tweets")
	fun getAllTweets(): LiveData<List<RecentTweetEntity>>
	
	@Query("DELETE FROM recent_tweets")
	fun deleteAllTweet():LiveData<Int>
	
	@Query("DELETE FROM recent_tweets WHERE tweet_id = :id")
	 fun deleteTweet(id:String):LiveData<Int>
}
