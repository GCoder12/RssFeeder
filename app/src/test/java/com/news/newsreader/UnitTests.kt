package com.news.newsreader

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import com.news.newsreader.model.api.RemoteDataSource
import com.news.newsreader.model.db.models.NewsModel
import com.news.newsreader.model.repo.Repository
import com.news.newsreader.ui.NewsViewModel
import com.nhaarman.mockitokotlin2.mock
import org.junit.Test
import org.mockito.Mockito.`when`


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
        val repository: Repository = mock()

        val viewModel = NewsViewModel(repository)
        viewModel.fetchNewsItems()
    }

    @Test
    fun getJsonFromXml() {

        val context: Context = mock()
        val assets: AssetManager = mock()
        `when`(context.assets).thenReturn(assets)
        val dataHelperMock: DataHelper = DataHelper.getInstance(context)
        val mutableList = parseFile(assets, dataHelperMock, RemoteDataSource.CATEGORY_FEED_1).toMutableList()
        mutableList.addAll(parseFile(assets, dataHelperMock, RemoteDataSource.CATEGORY_FEED_2))
        mutableList.addAll(parseFile(assets, dataHelperMock, RemoteDataSource.CATEGORY_FEED_3))
        mutableList.addAll(parseFile(assets, dataHelperMock, RemoteDataSource.CATEGORY_FEED_4))
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