package com.news.newsreader.model.repo

import androidx.lifecycle.LiveData
import com.news.newsreader.model.db.models.CategoryWithNews
import com.news.newsreader.model.db.models.NewsCategoryModel

interface Repository {

    suspend fun getNewsItemForCategories(categories : List<String>) : LiveData<List<CategoryWithNews>>
    suspend fun getAvailableCategories(): List<NewsCategoryModel>

}