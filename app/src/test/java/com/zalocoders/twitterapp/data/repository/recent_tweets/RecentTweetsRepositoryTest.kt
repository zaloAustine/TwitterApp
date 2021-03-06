package com.zalocoders.twitterapp.data.repository.recent_tweets


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.zalocoders.twitterapp.base.BaseTest
import com.zalocoders.twitterapp.base.getValueBlocking
import com.zalocoders.twitterapp.data.db.entities.RecentTweetEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class RecentTweetsRepositoryTest:BaseTest() {
	
	private lateinit var recentTweetsRepository: RecentTweetsRepository
	
	@get:Rule
	var instantTaskExecutorRule = InstantTaskExecutorRule()
	
	@ExperimentalCoroutinesApi
	@Before
	override fun setup() {
		super.setup()
		recentTweetsRepository = RecentTweetsRepository(tweetsDao)
	}
	
	@Test
	fun `test getting tweets from DB`() {
		runBlocking {
			val tweet = RecentTweetEntity("1", "test")
			recentTweetsRepository.insertTweet(tweet)
			val allTweets = recentTweetsRepository.getAllTweets()
			Truth.assertThat(allTweets.getValueBlocking()?.get(0)).isEqualTo(tweet)
		}
	}
	
	
	@Test
	fun `delete tweets from DB`() {
		runBlocking {
			val tweet = RecentTweetEntity("1","test")
			recentTweetsRepository.insertTweet(tweet)
			
			val delete = recentTweetsRepository.deleteTweet(tweet.tweet_id)
			Truth.assertThat(delete).isNotNull()
		}
	}
	
	@Test
	fun `insert tweets into DB`() {
		runBlocking {
			val tweet = RecentTweetEntity("1","test")
			recentTweetsRepository.insertTweet(tweet)
			
			val insert = recentTweetsRepository.insertTweet(tweet)
			Truth.assertThat(insert).isNotNull()
		}
	}
	
}