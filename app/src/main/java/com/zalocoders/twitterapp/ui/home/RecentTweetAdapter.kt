package com.zalocoders.twitterapp.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zalocoders.twitterapp.data.db.entities.RecentTweetEntity
import com.zalocoders.twitterapp.databinding.RecentTweetItemBinding


class RecentTweetAdapter(private val recentTweetClickListener: RecentTweetClickListener):
		ListAdapter<RecentTweetEntity, RecentTweetAdapter.RecentTweetViewHolder>(diffUtil) {
	inner class RecentTweetViewHolder(private val binding: RecentTweetItemBinding) :
			RecyclerView.ViewHolder(binding.root) {
		
		@SuppressLint("SetTextI18n")
		fun bind(item: RecentTweetEntity) {
			with(binding) {
			tweetText.text = item.tweet_desc
			}
			
			binding.deleteRecentTweet.setOnClickListener {
				recentTweetClickListener.deleteTweet(item.tweet_id)
			}
		}
	}
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentTweetViewHolder =
			RecentTweetViewHolder(
					RecentTweetItemBinding.inflate(
							LayoutInflater.from(parent.context), parent, false
					)
			)
	
	override fun onBindViewHolder(holder: RecentTweetViewHolder, position: Int) {
		holder.bind(getItem(position)!!)
	}
}

val diffUtil = object : DiffUtil.ItemCallback<RecentTweetEntity>() {
	override fun areItemsTheSame(oldItem: RecentTweetEntity, newItem: RecentTweetEntity): Boolean {
		return oldItem.tweet_id == newItem.tweet_id
	}
	
	override fun areContentsTheSame(oldItem: RecentTweetEntity, newItem: RecentTweetEntity): Boolean {
		return oldItem == newItem
	}
}
interface RecentTweetClickListener {
	fun deleteTweet(id:String)
}