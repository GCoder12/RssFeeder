package com.news.newsreader.model.repo

import com.news.newsreader.model.db.models.NewsModel
import java.util.*

class TestRepository {
    val testingNewsModels: List<NewsModel>
        get() {
            val list: MutableList<NewsModel> = ArrayList()
            list.add(NewsModel())
            return list
        }
}