package com.zalocoders.twitterapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zalocoders.twitterapp.data.db.entities.RecentTweetEntity
import com.zalocoders.twitterapp.data.repository.recent_tweets.RecentTweetsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
		private var recentTweetsRepository: RecentTweetsRepository
):ViewModel() {
	
	suspend fun insertTweet(tweet: RecentTweetEntity) = recentTweetsRepository.insertTweet(tweet)
	
	fun getAllTweets(): LiveData<List<RecentTweetEntity>> = recentTweetsRepository.getAllTweets()
	
	suspend fun deleteAllTweet() = recentTweetsRepository.deleteAllTweet()
	
	suspend fun deleteTweet(id:String) = recentTweetsRepository.deleteTweet(id)
}