package com.zalocoders.twitterapp.ui.search_results

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zalocoders.twitterapp.data.db.entities.RecentTweetEntity
import com.zalocoders.twitterapp.data.model.Tweet
import com.zalocoders.twitterapp.data.repository.search.ISearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@HiltViewModel
class SearchResultsViewModel @Inject constructor(
		private val searchRepository: ISearchRepository,

) : ViewModel(){
	
	private var _searchResult = MutableLiveData<PagingData<Tweet>>()
	val searchResult: LiveData<PagingData<Tweet>> = _searchResult
	
	 fun searchTweets(query:String) = viewModelScope.launch(IO) {
		searchRepository.searchTweet(query).cachedIn(viewModelScope).collectLatest {
			_searchResult.postValue(it)
		}
	 }
	
	
	  fun insertTweet(tweet: RecentTweetEntity){
	 	viewModelScope.launch(IO) {
			searchRepository.insertTweet(tweet)
		}
	 }
	
}