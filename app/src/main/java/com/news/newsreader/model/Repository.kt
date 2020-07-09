package com.news.newsreader.model

import androidx.lifecycle.LiveData
import com.news.newsreader.db.NewsDao

class Repository(val newsDao: NewsDao) {

    val items : LiveData<List<NewsItem>>
    //TODO add webservice

    init {
        items = newsDao.getNewsItems()
    }

    fun getNewsItems() {
        newsDao.getNewsItems();
    }


}