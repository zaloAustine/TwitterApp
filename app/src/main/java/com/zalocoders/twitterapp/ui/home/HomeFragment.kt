package com.zalocoders.twitterapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zalocoders.twitterapp.databinding.FragmentSearchBinding
import com.zalocoders.twitterapp.ui.search_results.RecentTweetAdapter
import com.zalocoders.twitterapp.utils.hide
import com.zalocoders.twitterapp.utils.hideSoftInput
import com.zalocoders.twitterapp.utils.show
import com.zalocoders.twitterapp.utils.showErrorSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
	
	private val homeViewModel:HomeViewModel by viewModels()
	
	private val binding :FragmentSearchBinding by lazy {
		FragmentSearchBinding.inflate(layoutInflater)
	}
	
	private lateinit var recentTweetAdapter: RecentTweetAdapter
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?): View = binding.root
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		recentTweetAdapter = RecentTweetAdapter()
		
		setUpSearch()
		setRecentUpRecyclerView()
		getRecentTweets()
		searchCloseListener()
	}
	
	private fun setUpSearch(){
		
		binding.searchView.isIconified = false
		hideSoftInput()
		
		binding.searchView.setOnClickListener{
			binding.searchView.onActionViewExpanded()
		}
		
		binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener, android.widget.SearchView.OnQueryTextListener {
			
			override fun onQueryTextChange(newText: String): Boolean {
				return false
			}
			
			override fun onQueryTextSubmit(query: String): Boolean {
				
				if(query.isNotEmpty()){
					navigateToResult(query)
				}else{
					binding.root.showErrorSnackbar("Enter valid Name")
					
				}
				hideSoftInput()
				return true
			}
		})
		
	}
	
	private fun searchCloseListener() {
		binding.searchView.setOnCloseListener {
			hideSoftInput()
			true
		}
	}
	
	private fun setRecentUpRecyclerView(){
		
		binding.tweetRecycleView.apply{
			adapter = recentTweetAdapter
			setHasFixedSize(true)
			layoutManager = LinearLayoutManager(context)
		}
	}
	
	private fun getRecentTweets(){
			homeViewModel.getAllTweets().observe(viewLifecycleOwner,{
				if(it.isNotEmpty()){
				recentTweetAdapter.submitList(it)
					binding.clearRecent.show()
				}else{
					//show empty state
					binding.emptyStateView.show()
					binding.clearRecent.hide()
					
				}
			})
	}
	
	private fun navigateToResult(queryString:String){
		val action = HomeFragmentDirections.actionSearchFragmentToResultFragment(queryString)
		findNavController().navigate(action)
	}
	
}