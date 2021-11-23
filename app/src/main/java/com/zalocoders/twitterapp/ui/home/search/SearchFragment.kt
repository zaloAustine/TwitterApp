package com.zalocoders.twitterapp.ui.home.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.zalocoders.twitterapp.databinding.FragmentSearchBinding
import com.zalocoders.twitterapp.utils.hide
import com.zalocoders.twitterapp.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
	
	private val viewModel: SearchViewModel by activityViewModels()
	
	private val binding :FragmentSearchBinding by lazy{
		FragmentSearchBinding.inflate(layoutInflater)
	}
	
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?): View = binding.root
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		observeLoading()
		
		binding.searchButton.setOnClickListener {
			search()
		}
	}
	
	private fun observeLoading(){
		viewModel.isLoading.observe(viewLifecycleOwner,{
			when(it){
				true -> {
					binding.searchProgress.show()
					binding.searchButton.hide()
				}
				false -> {
					binding.searchProgress.hide()
					binding.searchButton.show()
				}
			}
		})
	}
	
	private fun search(){
		viewLifecycleOwner.lifecycleScope.launch {
			viewModel.searchTweets("zalo")
		}
	}
}