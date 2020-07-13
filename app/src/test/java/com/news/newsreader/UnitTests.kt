package com.news.newsreader

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import com.news.newsreader.model.api.ApiService
import com.news.newsreader.model.db.models.NewsModel
import com.news.newsreader.model.repo.RepositoryImpl
import com.news.newsreader.ui.NewsViewModel
import com.nhaarman.mockitokotlin2.mock
import org.json.JSONObject
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


class UnitTests {

//    @Test //White box testing
//    fun testMocking() {
//        val application : Application = mock()
//        val repository : Repository = mock()
//        val viewModel = NewsViewModel(application)
//        viewModel.repository = repository
//        viewModel.fetchNewsItems()
//    }

    @Test
    fun testMockingNonInterface() {
        val application: Application = mock()
        val repository: RepositoryImpl = mock()
        val viewModel = NewsViewModel(application)
        viewModel.repository = repository
        viewModel.fetchNewsItems()
    }

    @Test
    fun getJsonFromXml() {

        val context: Context = mock()
        val assets: AssetManager = mock()
        `when`(context.assets).thenReturn(assets)
        val dataHelperMock: DataHelper = DataHelper.getInstance(context)
        val mutableList = parseFile(assets, dataHelperMock, ApiService.CATEGORY_FEED_1).toMutableList()
        mutableList.addAll(parseFile(assets, dataHelperMock, ApiService.CATEGORY_FEED_2))
        mutableList.addAll(parseFile(assets, dataHelperMock, ApiService.CATEGORY_FEED_3))
        mutableList.addAll(parseFile(assets, dataHelperMock, ApiService.CATEGORY_FEED_4))
        println("Mutable List ${mutableList.map { it.title.substring(0,10) }}")
    }

    private fun parseFile(
        assets: AssetManager, dataHelper: DataHelper,
        category: String
    ) : List<NewsModel> {
        val fileName = dataHelper.getFileNameForCategory(category)
        `when`(assets.open(fileName)).thenReturn(
            this.javaClass.classLoader.getResourceAsStream(fileName)
        )
        return dataHelper.getTestingListRow(category)
    }


}