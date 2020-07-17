package com.news.newsreader.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.news.newsreader.DataHelper
import com.news.newsreader.model.api.RemoteDataSource
import com.news.newsreader.model.db.NewsRoomDatabase
import com.news.newsreader.model.db.models.CategoryWithNews
import com.news.newsreader.model.db.models.NewsCategoryModel
import com.news.newsreader.model.di.AppContainer
import com.news.newsreader.model.repo.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(
    val repository: Repository,
    val items: LiveData<List<CategoryWithNews>>,
    val categories: LiveData<List<NewsCategoryModel>>
) : ViewModel() {


    fun updateDisplayedNews(categoriesToDisplay : List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateDisplayedCategories(categoriesToDisplay)
        }
    }

    fun fetchNewsItems() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNewsItems()
        }
    }

}