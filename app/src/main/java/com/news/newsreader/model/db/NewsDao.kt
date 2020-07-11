package com.news.newsreader.model.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.news.newsreader.model.db.models.CategoryWithNews
import com.news.newsreader.model.db.models.NewsCategoryModel
import com.news.newsreader.model.db.models.NewsModel
import org.jetbrains.annotations.TestOnly

@Dao
interface NewsDao {

//    @Query("SELECT * from news_item WHERE category=$feedCategory")//ORDER BY word ASC")
//    fun getNewsItems(feedCategory:String): LiveData<List<NewsModel>>

    @Transaction
    @Query("SELECT * from newscategorymodel")
    fun getNewsWithCategories() : LiveData<List<NewsCategoryModel>>

    @TestOnly
    @Transaction
    @Query("SELECT * from newscategorymodel")
    fun getNewsWithCategoriesListOnly() : List<CategoryWithNews>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(newsCategoryModel: NewsCategoryModel)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(newsModel: NewsModel)

    @Query("DELETE FROM newscategorymodel")
    suspend fun deleteAll()
}