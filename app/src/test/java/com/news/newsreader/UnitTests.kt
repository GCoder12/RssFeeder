package com.news.newsreader

import android.app.Application
import com.news.newsreader.model.repo.Repository
import com.news.newsreader.model.repo.RepositoryImpl
import com.news.newsreader.ui.NewsViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test


class UnitTests {

    @Test //White box testing
    fun testMocking() {
        val application : Application = mock()
        val repository : Repository = mock()
        val viewModel = NewsViewModel(application)
        viewModel.repository = repository
        viewModel.fetchNewsItems()
        verify(repository).getNewsItems()
    }

    @Test
    fun testMockingNonInterface() {
        val application : Application = mock()
        val repository : RepositoryImpl = mock()
        val viewModel = NewsViewModel(application)
        viewModel.repository = repository
        viewModel.fetchNewsItems()
        verify(repository).getNewsItems()
    }

}