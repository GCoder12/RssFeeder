package com.news.newsreader.model.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.news.newsreader.DataHelper
import com.news.newsreader.model.api.RemoteDataSource
import com.news.newsreader.model.db.NewsDao
import com.news.newsreader.model.db.NewsRoomDatabase
import com.news.newsreader.model.repo.Repository
import com.news.newsreader.ui.ViewModelFactory

class AppContainer(application: Application) {


    private val localDataSource = NewsRoomDatabase.getDatabase(application).NewsDao()

    /**
     * DataHelper doesn't need to be a singleton here since remoteDataSource is already that
     */
    private val remoteDataSource = RemoteDataSource(DataHelper(application))

    val repository = Repository(localDataSource,remoteDataSource)

    /**
     * //TODO change this manual DI to be a singleton shared across activity with fragments.
     * //TODO most likeyl using HILT
     */
    val viewModelFactory = ViewModelFactory(repository)

}