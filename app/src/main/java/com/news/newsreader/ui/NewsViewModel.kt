package com.news.newsreader.ui

import androidx.lifecycle.*
import com.news.newsreader.model.db.models.CategoryWithNews
import com.news.newsreader.model.db.models.NewsCategoryModel
import com.news.newsreader.model.repo.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//TODO change the Single source of truth to be the Room database
class NewsViewModel(
    val repository: Repository

) : ViewModel() {

    /**
     * List of news items as a list in their respective categories' model
     */
    val items: MutableLiveData<List<CategoryWithNews>> = MutableLiveData()

    /**
     * Categories only
     */
    val categories: MutableLiveData<List<NewsCategoryModel>> = MutableLiveData()

    /**
     * Update displayed categories
     * @param categoriesToDisplay - categories to update to display = true
     */
    fun updateDisplayedNews(categoriesToDisplay : List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            items.postValue(repository.updateDisplayedCategories(categoriesToDisplay,categories.value))
        }
    }

    /**
     * Return all categories, regardless of display value
     */
    fun getAllCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            categories.postValue(repository.getAllCategories())
        }
    }

    /**
     * Gets the news items for displayedCategories
     */
    fun fetchNewsItems() {
        viewModelScope.launch(Dispatchers.IO) {
            items.postValue(repository.getNewsItems())
        }
    }

}