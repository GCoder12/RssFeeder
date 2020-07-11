package com.news.newsreader.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.news.newsreader.model.db.models.CategoryWithNews
import com.news.newsreader.model.db.models.NewsCategoryModel
import com.news.newsreader.model.db.models.NewsModel
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(NewsCategoryModel::class,NewsModel::class), version = 1, exportSchema = false)
abstract class NewsRoomDatabase : RoomDatabase() {

    abstract fun NewsDao(): NewsDao

    companion object {
        var INSTANCE: NewsRoomDatabase? = null

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
                return instance
            }

        }

    }

}