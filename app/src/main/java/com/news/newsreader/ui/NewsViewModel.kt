package com.news.newsreader.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.news.newsreader.model.api.ApiService
import com.news.newsreader.model.db.models.NewsModel
import com.news.newsreader.model.db.NewsRoomDatabase
import com.news.newsreader.model.db.models.NewsCategoryModel
import com.news.newsreader.model.repo.Repository
import com.news.newsreader.model.repo.RepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(
    application: Application
) : AndroidViewModel(application) {


    var repository : Repository

    val items : LiveData<List<NewsCategoryModel>>

    init {
        val database = NewsRoomDatabase.getDatabase(application,viewModelScope)
        val service = ApiService()
        repository = RepositoryImpl(database.NewsDao(),service)
        items = (repository as RepositoryImpl).items
    }

    fun fetchNewsItems() {
         viewModelScope.launch(Dispatchers.IO) {
         repository.getNewsItems()
         }
    }

}