package com.zalocoders.twitterapp.data.db.tweet

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zalocoders.twitterapp.data.db.entities.RecentTweetEntity

@Database(entities = [RecentTweetEntity::class], version = 4, exportSchema = false)
abstract class TwitterDatabase:RoomDatabase() {
	abstract fun tweetsDao(): TweetsDao
}