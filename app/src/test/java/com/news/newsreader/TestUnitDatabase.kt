package com.news.newsreader

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.news.newsreader.db.NewsDao
import com.news.newsreader.db.NewsRoomDatabase
import com.news.newsreader.model.NewsItem
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class TestUnitDatabase {

    private lateinit var dao : NewsDao
    private lateinit var db : NewsRoomDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context,NewsRoomDatabase::class.java).build()
        dao = db.NewsDao()

    }

    @Test
    fun testWrite() = runBlocking<Unit> {
        //This doesn't run with robo electric context for db
        val newsItem = NewsItem("guid","title","link","desc","imageUrl")
        dao.insert(newsItem)
        val list = dao.getNewsItems().value
        assert(list!=null) {"null list"}
        assert(list?.contains(newsItem) ?: false) {"list doesn't contain item"}
    }
}