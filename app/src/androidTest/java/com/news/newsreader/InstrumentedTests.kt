package com.news.newsreader

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.news.newsreader.model.db.NewsDao
import com.news.newsreader.model.db.models.NewsModel
import com.news.newsreader.model.db.NewsRoomDatabase
import com.news.newsreader.model.db.models.NewsCategoryModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test


class InstrumentedTests {

    private lateinit var dao : NewsDao
    private lateinit var db : NewsRoomDatabase
    private lateinit var context: Context

//    @Mock
//    lateinit var repository : TestRepository

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context,NewsRoomDatabase::class.java).build()
        dao = db.NewsDao()

    }

    @Test
    fun testWrite() = runBlocking<Unit> {
        val cat1 = "category1"
        val cat2 = "category2"
        launch {
            val newsModel1 = NewsModel(
                "guid",
                "title",
                "link",
                "desc",
                "imageUrl",
                cat1.hashCode().toLong()
            )
            val newsModel2 = NewsModel(
                "guid",
                "title",
                "link",
                "desc",
                "imageUrl",
                cat2.hashCode().toLong()
            )


            dao.insert(NewsCategoryModel(cat1))
            dao.insert(NewsCategoryModel(cat2))
            dao.insert(newsModel1)
            dao.insert(newsModel2)
            val list = dao.getNewsCategoriesListOnly()
            Log.d("TESTS", "LIST is ${list}")
            assert(list != null)
        }
    }


}