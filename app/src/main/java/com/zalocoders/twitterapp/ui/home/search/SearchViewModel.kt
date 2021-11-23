package com.zalocoders.twitterapp.ui.home.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.zalocoders.twitterapp.data.model.Tweet
import com.zalocoders.twitterapp.data.repository.search.ISearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
		private val searchRepository: ISearchRepository
) : ViewModel(){
	
	private val _isLoading = MutableLiveData<Boolean>()
	val isLoading: LiveData<Boolean> = _isLoading
	
	private var _searchResult = MutableLiveData<PagingData<Tweet>>()
	val searchResult: LiveData<PagingData<Tweet>> = _searchResult
	
	suspend fun searchTweets(query:String){
		_isLoading.postValue(true)
		val result = searchRepository.searchTweet(query).cachedIn(viewModelScope)
		_searchResult.value = result.value
		_isLoading.postValue(false)
	}
	
}