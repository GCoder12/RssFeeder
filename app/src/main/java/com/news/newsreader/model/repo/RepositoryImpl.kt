package com.news.newsreader.model.repo

import androidx.lifecycle.LiveData
import com.news.newsreader.model.api.ApiService
import com.news.newsreader.model.db.NewsDao
import com.news.newsreader.model.db.models.NewsCategoryModel
import com.news.newsreader.model.db.models.NewsModel

class RepositoryImpl(val newsDao: NewsDao,
    val apiService: ApiService) : Repository {

    val items : LiveData<List<NewsCategoryModel>>


    init {
        items = newsDao.getNewsWithCategories()
    }

    override fun getNewsItems() : LiveData<List<NewsCategoryModel>> {

        return newsDao.getNewsWithCategories()
    }

    override fun getAvailableCategories(): List<String> {
        TODO("Not yet implemented")
    }


}