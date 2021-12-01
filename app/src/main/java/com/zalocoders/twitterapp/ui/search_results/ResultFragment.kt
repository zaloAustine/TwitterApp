package com.zalocoders.twitterapp.ui.search_results

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.zalocoders.twitterapp.databinding.FragmentResultBinding
import com.zalocoders.twitterapp.ui.base.TweetAdapter
import com.zalocoders.twitterapp.utils.hideSoftInput
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment : Fragment() {
	
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
		
		tweetAdapter = TweetAdapter()
		
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
				binding.progressBar.visibility = View.VISIBLE
			}
			else{
				binding.progressBar.visibility = View.GONE
				val error = when {
					loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
					loadState.append is LoadState.Error -> loadState.append as LoadState.Error
					loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
					else -> null
				}
				error?.let {
					Toast.makeText(context, it.error.message, Toast.LENGTH_LONG).show()
				}
			}
		}
	}
	
	private fun getSearchResults() {
		viewModel.searchResult.observe(viewLifecycleOwner,{
			tweetAdapter.submitData(lifecycle,it)
		})
		
	}
	
}