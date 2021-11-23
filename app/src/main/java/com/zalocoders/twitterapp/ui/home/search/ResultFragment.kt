package com.zalocoders.twitterapp.ui.home.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.zalocoders.twitterapp.databinding.FragmentResultBinding
import com.zalocoders.twitterapp.ui.home.base.TweetAdapter
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ResultFragment : Fragment() {
	
	private val binding:FragmentResultBinding by lazy {
		FragmentResultBinding.inflate(layoutInflater)
	}
	
	private val viewModel: SearchViewModel by activityViewModels()
	
	private lateinit var tweetAdapter: TweetAdapter
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?): View  = binding.root
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		tweetAdapter = TweetAdapter()
		
		setUpLoading()
		setUpRecyclerView()
		getSearchResults()
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
					Timber.e("Error ${it.error.localizedMessage}")
				}
			}
		}
	}
	
	private fun getSearchResults() {
//			viewModel.searchResult.observe(viewLifecycleOwner, {
//
//			})
		}
}