package com.news.newsreader.model.repo

import androidx.lifecycle.LiveData
import com.news.newsreader.model.db.models.NewsCategoryModel
import com.news.newsreader.model.db.models.NewsModel

interface Repository {

    fun getNewsItems() : LiveData<List<NewsCategoryModel>>
    fun getAvailableCategories() : List<String>

}