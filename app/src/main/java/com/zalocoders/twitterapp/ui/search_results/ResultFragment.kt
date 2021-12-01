package com.zalocoders.twitterapp.ui.search_results

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.zalocoders.twitterapp.data.db.entities.toRecentTweetEntity
import com.zalocoders.twitterapp.data.model.Tweet
import com.zalocoders.twitterapp.databinding.FragmentResultBinding
import com.zalocoders.twitterapp.utils.hide
import com.zalocoders.twitterapp.utils.hideSoftInput
import com.zalocoders.twitterapp.utils.show
import com.zalocoders.twitterapp.utils.showErrorSnackbar
import com.zalocoders.twitterapp.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment : Fragment(),TweetClickListener {
	
	private val binding:FragmentResultBinding by lazy {
		FragmentResultBinding.inflate(layoutInflater)
	}
	
	private val viewModel: SearchResultsViewModel by viewModels()
	private val navArgs:ResultFragmentArgs by navArgs()
	
	private lateinit var tweetAdapter: TweetAdapter
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?): View  = binding.root
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		search(navArgs.query)
		
		tweetAdapter = TweetAdapter(this)
		
		setUpRecyclerView()
		setUpLoading()
		getSearchResults()
		
	}
	
	private fun search(query: String){
		hideSoftInput()
		lifecycleScope.launchWhenStarted {
			viewModel.searchTweets(query)
		}
	}
	
	private fun setUpRecyclerView(){
		
		binding.tweetRecycleView.apply{
			adapter = tweetAdapter
			setHasFixedSize(true)
			layoutManager = LinearLayoutManager(context)
		}
	}
	
	private fun setUpLoading(){
		tweetAdapter.addLoadStateListener { loadState ->
			
			
			if (loadState.refresh is LoadState.Loading){
				binding.progressView.show()
			}
			else{
				binding.progressView.hide()
				val error = when {
					loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
					loadState.append is LoadState.Error -> loadState.append as LoadState.Error
					loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
					else -> null
				}
				error?.let {
					binding.root.showErrorSnackbar(it.error.message.toString(),Snackbar.LENGTH_LONG)
				}
			}
		}
	}
	
	private fun getSearchResults() {
		viewModel.searchResult.observe(viewLifecycleOwner,{
			tweetAdapter.submitData(lifecycle,it)
		})
		
	}
	
	override fun insertTweet(item: Tweet) {
	viewModel.insertTweet(item.toRecentTweetEntity()).observe(viewLifecycleOwner,{
		if(it != null){
			binding.root.showSnackbar("Added Successfully",Snackbar.LENGTH_LONG)
		}
	})
	}
	
	override fun deleteTweet(item: Tweet) {
		viewModel.deleteTweet(item.id).observe(viewLifecycleOwner,{
			if(it != null){
				binding.root.showSnackbar("Deleted Successfully",Snackbar.LENGTH_LONG)
			}
		})
	}
	
}