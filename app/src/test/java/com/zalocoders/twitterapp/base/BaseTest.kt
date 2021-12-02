package com.zalocoders.twitterapp.base

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zalocoders.twitterapp.data.api.ApiService
import com.zalocoders.twitterapp.data.db.tweet.TweetsDao
import com.zalocoders.twitterapp.data.db.tweet.TwitterDatabase
import com.zalocoders.twitterapp.data.repository.search.SearchDataSource
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

open class BaseTest {
	// database and dao
	open lateinit var tweeterDatabase:TwitterDatabase
	open lateinit var tweetsDao: TweetsDao
	
	// mock web server and network api
	open lateinit var mockWebServer: MockWebServer
	open lateinit var okHttpClient: OkHttpClient
	open lateinit var loggingInterceptor: HttpLoggingInterceptor
	open lateinit var apiService: ApiService
	open lateinit var searchDataSource: SearchDataSource
	
	
	
	@ExperimentalCoroutinesApi
	@Before
	open fun setup() {
		val context = ApplicationProvider.getApplicationContext<Context>()
		
		val testDispatcher = TestCoroutineDispatcher()
		
		tweeterDatabase = Room.inMemoryDatabaseBuilder(context,
				TwitterDatabase::class.java)
				.allowMainThreadQueries()
				.setTransactionExecutor(testDispatcher.asExecutor())
				.setQueryExecutor(testDispatcher.asExecutor())
				.build()
		
		
		tweetsDao = tweeterDatabase.tweetsDao()
		
		
		mockWebServer = MockWebServer().apply {
			dispatcher = RequestDispatcher()
			start()
		}
		
		loggingInterceptor = HttpLoggingInterceptor().apply {
			level = HttpLoggingInterceptor.Level.BODY
		}
		
		okHttpClient = buildOkhttpClient(loggingInterceptor)
		
		val moshi = Moshi.Builder()
				.add(KotlinJsonAdapterFactory())
				.build()
		
		apiService = Retrofit.Builder()
				.baseUrl(mockWebServer.url("/"))
				.client(okHttpClient)
				.addConverterFactory(MoshiConverterFactory.create(moshi))
				.build()
				.create(ApiService::class.java)
		
		searchDataSource = SearchDataSource(apiService,"tweet")
		
	}
	
	@After
	@Throws(IOException::class)
	open fun tearDown() {
		tweeterDatabase.close()
		mockWebServer.shutdown()
	}
	
	private fun buildOkhttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
		return OkHttpClient.Builder()
				.addInterceptor(httpLoggingInterceptor)
				.connectTimeout(60, TimeUnit.SECONDS)
				.readTimeout(60, TimeUnit.SECONDS)
				.build()
	}
}