package com.news.newsreader.model.api

import com.news.newsreader.DataHelper
import com.news.newsreader.model.db.models.NewsModel

/**
 * Gets data for feed from web
 */
class ApiService(val helper: DataHelper) {

    companion object {
        val CATEGORY_FEED_1 = "Nasa - Breaking News"
        val CATEGORY_FEED_2 = "Buzzfeed - Latest"
        val CATEGORY_FEED_3 = "Buzzfeed - LOL"
        val CATEGORY_FEED_4 = "Buzzfeed - Animals"
    }


    fun getFeed(category: String): ArrayList<NewsModel> {
        return ArrayList(helper.getTestingListRow(category))
    }

    fun getCategories() : List<String> {
        return listOf(CATEGORY_FEED_1, CATEGORY_FEED_2, CATEGORY_FEED_3, CATEGORY_FEED_4)
    }

}