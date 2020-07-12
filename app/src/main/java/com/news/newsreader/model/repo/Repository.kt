package com.news.newsreader.model.repo

import androidx.lifecycle.LiveData
import com.news.newsreader.model.db.models.CategoryWithNews
import com.news.newsreader.model.db.models.NewsCategoryModel
import com.news.newsreader.model.db.models.NewsModel

interface Repository {

    suspend fun getCategoriesWithNews() : LiveData<List<CategoryWithNews>>
    fun getAvailableCategories() : List<String>

}