package com.news.newsreader.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.news.newsreader.model.NewsItem
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(NewsItem::class), version = 1, exportSchema = false)
abstract class NewsRoomDatabase : RoomDatabase() {

    abstract fun NewsDao(): NewsDao

    companion object {
        lateinit var INSTANCE: NewsRoomDatabase

        fun getDatabase(context: Context, scope: CoroutineScope): NewsRoomDatabase {
            val TEMP_INSTANCE = INSTANCE
            if (TEMP_INSTANCE != null) {
                return TEMP_INSTANCE
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    NewsRoomDatabase::class.java,
                    "NewsDb"
                ).addCallback(NewsRoomDbCallback(scope)).build()
                INSTANCE = instance
                return INSTANCE
            }

        }

    }

}