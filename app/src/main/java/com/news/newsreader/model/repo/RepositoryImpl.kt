package com.news.newsreader.model.repo

import androidx.lifecycle.LiveData
import com.news.newsreader.model.api.ApiService
import com.news.newsreader.model.db.NewsDao
import com.news.newsreader.model.db.models.CategoryWithNews
import com.news.newsreader.model.db.models.NewsCategoryModel

class RepositoryImpl(
    val newsDao: NewsDao,
    val apiService: ApiService
) : Repository {

    val items: LiveData<List<CategoryWithNews>>


    init {
        items = newsDao.getNewsCategoriesList()
    }


    override suspend fun getCategoriesWithNews(): LiveData<List<CategoryWithNews>> {

        newsDao.deleteCategories()
        newsDao.deleteNewsItems()
        val newsList = apiService.getFeed(ApiService.CATEGORY_FEED_1)
        newsList.addAll(apiService.getFeed(ApiService.CATEGORY_FEED_2))
        newsList.addAll(apiService.getFeed(ApiService.CATEGORY_FEED_3))
        newsList.addAll(apiService.getFeed(ApiService.CATEGORY_FEED_4))
        newsList.addAll(apiService.getFeed(ApiService.CATEGORY_FEED_5))
        val categoryList = arrayListOf(
            NewsCategoryModel(ApiService.CATEGORY_FEED_1),
            NewsCategoryModel(ApiService.CATEGORY_FEED_2),
            NewsCategoryModel(ApiService.CATEGORY_FEED_3),
            NewsCategoryModel(ApiService.CATEGORY_FEED_4),
            NewsCategoryModel(ApiService.CATEGORY_FEED_5)
        )

        categoryList.forEach {
            newsDao.insert(it)
        }
        newsList.forEach{
            newsDao.insert(it)
        }
        return newsDao.getNewsCategoriesList()

    }

    override fun getAvailableCategories(): List<String> {
        TODO("Not yet implemented")
    }


}