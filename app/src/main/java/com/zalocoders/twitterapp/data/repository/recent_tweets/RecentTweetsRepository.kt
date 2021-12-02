package com.zalocoders.twitterapp.data.repository.recent_tweets

import androidx.lifecycle.LiveData
import com.zalocoders.twitterapp.data.db.entities.RecentTweetEntity
import com.zalocoders.twitterapp.data.db.tweet.TweetsDao
import javax.inject.Inject

class RecentTweetsRepository @Inject constructor(
		private val tweetsDao: TweetsDao
) {
	
	fun getAllTweets(): LiveData<List<RecentTweetEntity>> = tweetsDao.getAllTweets()
	
	suspend fun deleteAllTweet() = tweetsDao.deleteAllTweet()
	
	suspend fun deleteTweet(id:String) = tweetsDao.deleteTweet(id)
	
	suspend fun insertTweet(tweet: RecentTweetEntity) = tweetsDao.insertTweet(tweet)
	
}