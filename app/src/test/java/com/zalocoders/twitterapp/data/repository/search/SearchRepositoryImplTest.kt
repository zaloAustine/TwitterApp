package com.zalocoders.twitterapp.data.repository.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.io.Resources
import com.google.common.truth.Truth
import com.zalocoders.twitterapp.base.BaseTest
import com.zalocoders.twitterapp.base.convertJsonStringToObject
import com.zalocoders.twitterapp.data.model.SearchResponse
import java.io.File
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchRepositoryImplTest:BaseTest(){
	
	private lateinit var searchRepositoryImpl: SearchRepositoryImpl
	
	@get:Rule
	var instantTaskExecutorRule = InstantTaskExecutorRule()
	
	
	@ExperimentalCoroutinesApi
	@Before
	override fun setup() {
		super.setup()
		searchRepositoryImpl = SearchRepositoryImpl(apiService,tweetsDao)
	}
	
	
	@Test
	fun `test searching with pagination`() {
		runBlocking {
			
			val tweetArray = convertJsonStringToObject(getJson("json/response.json"),SearchResponse::class.java)
			
			val pagingSource = SearchDataSource(apiService,"test")
			
			Truth.assertThat(
					pagingSource.load(
							PagingSource.LoadParams.Refresh(
									key = null,
									loadSize = 1,
									placeholdersEnabled = false
							)
			)).isEqualTo(PagingSource.LoadResult.Page(
					data = listOf(tweetArray.data[0],tweetArray.data[1]),
					prevKey = null,
					nextKey = 2
			))
			
		}
	}
	
	private fun getJson(path: String): String {
		val uri = Resources.getResource(path)
		val file = File(uri.path)
		return String(file.readBytes())
	}
}