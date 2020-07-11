package com.news.newsreader

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.news.newsreader.model.db.NewsDao
import com.news.newsreader.model.db.models.NewsModel
import com.news.newsreader.model.db.NewsRoomDatabase
import com.news.newsreader.model.db.models.CategoryWithNews
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
        launch {
            val newsModel = NewsModel(
                "guid",
                "title",
                "link",
                "desc",
                "imageUrl",
                "category"
            )
            val newsCategory = NewsCategoryModel("category")

            dao.insert(newsCategory)
            dao.insert(newsModel)
            val list = dao.getNewsWithCategoriesListOnly()
            Log.d("TESTS", "LIST is $list")
            assert(list != null)
        }
    }


}