package com.zalocoders.twitterapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zalocoders.twitterapp.data.db.entities.RecentTweetEntity
import com.zalocoders.twitterapp.data.repository.recent_tweets.RecentTweetsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
		private var recentTweetsRepository: RecentTweetsRepository
):ViewModel() {
	
	fun getAllTweets(): LiveData<List<RecentTweetEntity>> = recentTweetsRepository.getAllTweets()
	
	 fun deleteAllTweet(){
		viewModelScope.launch(IO) {
			recentTweetsRepository.deleteAllTweet()
		}
	}
	
	fun deleteTweet(id:String){
		viewModelScope.launch(IO) {
			recentTweetsRepository.deleteTweet(id)
		}
	}
	
}