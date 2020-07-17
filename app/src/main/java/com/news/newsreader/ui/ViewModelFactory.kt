package com.news.newsreader.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.news.newsreader.model.repo.Repository
import java.lang.Exception

class ViewModelFactory(val repository: Repository) : ViewModelProvider.Factory  {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(repository,repository.items,repository.categories) as T
        }
        throw Exception("Invalid view model type")
    }
}