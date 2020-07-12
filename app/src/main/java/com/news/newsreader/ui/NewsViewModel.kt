package com.news.newsreader.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.news.newsreader.model.api.ApiService
import com.news.newsreader.model.db.NewsRoomDatabase
import com.news.newsreader.model.db.models.CategoryWithNews
import com.news.newsreader.model.db.models.NewsCategoryModel
import com.news.newsreader.model.repo.Repository
import com.news.newsreader.model.repo.RepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class NewsViewModel(
    application: Application
) : AndroidViewModel(application) {


    var repository: RepositoryImpl

    val items: LiveData<List<CategoryWithNews>>
    val categories: MutableLiveData<List<NewsCategoryModel>> by lazy {
        MutableLiveData<List<NewsCategoryModel>>()
    }


    init {
        val database = NewsRoomDatabase.getDatabase(application, viewModelScope)
        val service = ApiService()
        repository = RepositoryImpl(database.NewsDao(), service)
        items = (repository as RepositoryImpl).items
    }



    fun fetchNewsItems() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getNewsItems()
        }
    }

}