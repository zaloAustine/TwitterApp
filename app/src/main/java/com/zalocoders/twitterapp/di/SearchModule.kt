package com.zalocoders.twitterapp.di

import com.zalocoders.twitterapp.data.repository.search.ISearchRepository
import com.zalocoders.twitterapp.data.repository.search.SearchRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object SearchModule {
	
	@Provides
	fun provideSearchRepository(
			searchRepository: SearchRepositoryImpl
	): ISearchRepository = searchRepository
}