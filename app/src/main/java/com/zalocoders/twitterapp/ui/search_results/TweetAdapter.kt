package com.zalocoders.twitterapp.ui.search_results

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zalocoders.twitterapp.data.model.Tweet
import com.zalocoders.twitterapp.databinding.TweetItemBinding
import com.zalocoders.twitterapp.utils.hide


class TweetAdapter(private val tweetClickListener: TweetClickListener) :
		PagingDataAdapter<Tweet, TweetAdapter.TweetViewHolder>(diffUtil) {
	inner class TweetViewHolder(private val binding: TweetItemBinding) :
			RecyclerView.ViewHolder(binding.root) {
		
		@SuppressLint("SetTextI18n")
		fun bind(item: Tweet) {
			with(binding) {
			tweetText.text = item.text
			}
			
			binding.btnAddToRecent.setOnClickListener {
				tweetClickListener.insertTweet(item)
				binding.btnAddToRecent.hide()
			}
		}
	}
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetViewHolder =
			TweetViewHolder(
					TweetItemBinding.inflate(
							LayoutInflater.from(parent.context), parent, false
					)
			)
	
	override fun onBindViewHolder(holder: TweetViewHolder, position: Int) {
		holder.bind(getItem(position)!!)
	}
}

val diffUtil = object : DiffUtil.ItemCallback<Tweet>() {
	override fun areItemsTheSame(oldItem: Tweet, newItem: Tweet): Boolean {
		return oldItem.id == newItem.id
	}
	
	override fun areContentsTheSame(oldItem: Tweet, newItem: Tweet): Boolean {
		return oldItem == newItem
	}
}

interface TweetClickListener {
	fun insertTweet(item:Tweet)
}