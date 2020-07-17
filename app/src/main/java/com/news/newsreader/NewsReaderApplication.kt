package com.news.newsreader

import android.app.Application
import com.news.newsreader.model.di.AppContainer

class NewsReaderApplication : Application() {

    lateinit var AppContainer : AppContainer
    override fun onCreate() {
        super.onCreate()
        AppContainer = AppContainer(this)
    }
}