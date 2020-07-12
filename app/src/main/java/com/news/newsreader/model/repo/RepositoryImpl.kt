package com.news.newsreader.model.repo

import androidx.lifecycle.LiveData
import com.news.newsreader.model.api.ApiService
import com.news.newsreader.model.db.NewsDao
import com.news.newsreader.model.db.models.CategoryWithNews
import com.news.newsreader.model.db.models.NewsCategoryModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepositoryImpl(
    val newsDao: NewsDao,
    val apiService: ApiService
) {

    fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

    val items: LiveData<List<CategoryWithNews>>
    val categories : LiveData<List<NewsCategoryModel>>


    init {
        items = newsDao.getNewsCategoriesToDisplay()
        categories = newsDao.getPossibleCategories()
    }


    /**
     * Gets all categories, and corresponding news items from webservice,
     * and updates or adds them to DB.
     */
    suspend fun getNewsItems(): LiveData<List<CategoryWithNews>> {
        log("getCategoriesToDisplay()")
        //Only delete news item, categories can stay
        newsDao.deleteNewsItems()

        //Get all categories from webservice
        val allCategories = apiService.getCategories()

        //Insert into db if not exists
        allCategories.forEach {
            newsDao.insert(NewsCategoryModel(it))
        }

        //Insert all news items pertaining to categories
        allCategories.forEach {
            apiService.getFeed(it).forEach{
                newsDao.insert(it)
            }
        }
        newsDao.getPossibleCategories()
        //Fire off observable for displayed news and categories
        return newsDao.getNewsCategoriesToDisplay()

    }

    suspend fun updateDisplayedCategories(displayedCategories : List<String>) {
        val categorySet = displayedCategories
        categories.value?.let {
            it.forEach {newsCategoryModel ->
                if (categorySet.contains(newsCategoryModel.category)) newsCategoryModel.isDisplayed = true
                else newsCategoryModel.isDisplayed = false
                newsDao.update(newsCategoryModel)
            }
        }
    }

    suspend fun getPossibleCategories() : LiveData<List<NewsCategoryModel>> {
        //Fire off observable for all possible categories
        return newsDao.getPossibleCategories()
    }


}