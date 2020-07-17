package com.news.newsreader.model.repo

import com.news.newsreader.model.api.RemoteDataSource
import com.news.newsreader.model.db.NewsDao
import com.news.newsreader.model.db.models.CategoryWithNews
import com.news.newsreader.model.db.models.NewsCategoryModel

class Repository(
    val localDataSource: NewsDao,
    val remoteDataSource: RemoteDataSource
) {

    fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

    /**
     * Gets all categories, and corresponding news items from apiservice,
     * and updates or adds them to DB.
     */
    suspend fun getNewsItems(): List<CategoryWithNews> {
        log("getCategoriesToDisplay()")
        //Only delete news item, categories can stay
        localDataSource.deleteNewsItems()

        //Get all categories from webservice
        val allCategories = remoteDataSource.getCategories()

        //Insert into db if not exists
        allCategories.forEach {
            localDataSource.insert(NewsCategoryModel(it))
        }

        //Insert all news items pertaining to categories
        allCategories.forEach {
            remoteDataSource.getFeed(it).forEach {
                localDataSource.insert(it)
            }
        }

        //Fire off observable for displayed news and categories
        return localDataSource.getNewsItemsWithCategories()

    }

    fun getAllCategories(): List<NewsCategoryModel> {
        return localDataSource.getAllCategories()
    }

    suspend fun updateDisplayedCategories(
        displayedCategories: List<String>,
        categories: List<NewsCategoryModel>?
    ) : List<CategoryWithNews> {
        val categorySet = displayedCategories
        categories?.forEach { newsCategoryModel ->
            if (categorySet.contains(newsCategoryModel.category)) newsCategoryModel.isDisplayed =
                true
            else newsCategoryModel.isDisplayed = false
            localDataSource.update(newsCategoryModel)

        }
        return localDataSource.getNewsItemsWithCategories()
    }


}