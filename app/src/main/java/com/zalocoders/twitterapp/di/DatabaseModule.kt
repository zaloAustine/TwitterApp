package com.zalocoders.twitterapp.di

import android.content.Context
import androidx.room.Room
import com.zalocoders.twitterapp.data.db.tweet.TwitterDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
	
	@Singleton
	@Provides
	fun provideTwitterDatabase(@ApplicationContext context: Context) =
			Room.databaseBuilder(context, TwitterDatabase::class.java, "twitter_db")
					.fallbackToDestructiveMigration()
					.build()
	
	@Singleton
	@Provides
	fun provideTwitterDao(database: TwitterDatabase) = database.tweetsDao()

}
