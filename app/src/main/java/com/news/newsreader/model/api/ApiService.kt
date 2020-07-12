package com.news.newsreader.model.api

import com.news.newsreader.DataHelper
import com.news.newsreader.model.db.models.NewsModel

/**
 * Gets data for feed from web
 */
class ApiService {

    companion object {
        val CATEGORY_FEED_1 = "Category 1"
        val CATEGORY_FEED_2 = "Category 2"
        val CATEGORY_FEED_3 = "Category 3"
        val CATEGORY_FEED_4 = "Category 4"
        val CATEGORY_FEED_5 = "Category 5"
    }


    fun getFeed(category: String): ArrayList<NewsModel> {
        return ArrayList(DataHelper.getTestingListRow(category))
    }

    fun getCategories() : List<String> {
        return listOf(CATEGORY_FEED_1, CATEGORY_FEED_2, CATEGORY_FEED_3, CATEGORY_FEED_4, CATEGORY_FEED_5)
    }

}