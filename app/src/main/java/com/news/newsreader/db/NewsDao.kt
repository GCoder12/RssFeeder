package com.news.newsreader.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.news.newsreader.model.NewsItem

@Dao
interface NewsDao {

    @Query("SELECT * from news_item ")//ORDER BY word ASC")
    fun getNewsItems(): LiveData<List<NewsItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(newsItem: NewsItem)

    @Query("DELETE FROM news_item")
    suspend fun deleteAll()
}