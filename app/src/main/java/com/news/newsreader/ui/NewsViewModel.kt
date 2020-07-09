package com.news.newsreader.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.news.newsreader.model.NewsItem
import com.news.newsreader.db.NewsRoomDatabase
import com.news.newsreader.model.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(
    application: Application
) : AndroidViewModel(application) {


    val repository : Repository
    val items : LiveData<List<NewsItem>>

    init {
        val database = NewsRoomDatabase.getDatabase(application,viewModelScope)
        repository = Repository(database.NewsDao())
        items = repository.items
    }

    fun fetchNewsItems() {
         viewModelScope.launch(Dispatchers.IO) {
             repository.getNewsItems()
         }
    }

}