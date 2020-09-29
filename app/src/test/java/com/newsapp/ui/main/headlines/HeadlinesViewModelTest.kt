package com.newsapp.ui.main.headlines

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.newsapp.data.entities.News
import com.newsapp.data.entities.Source
import com.newsapp.data.remote.Resource
import com.newsapp.data.repositories.NewsRepository
import com.newsapp.utils.getOrAwaitValue
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HeadlinesViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository: NewsRepository = mockk()
    private var result: Observer<Resource<List<News>>> = mockk()
    private lateinit var viewModel: HeadlinesViewModel

    @Before
    fun `init repo and view model`() {
        val newsTest = News(2,Source(null,"testSource"),"titleTest","descTest","URL","image","2020-09-21","contentTest",false)

        every { repository.getHeadlines() } returns MutableLiveData<Resource<List<News>>>().also {
            it.value = Resource.success(listOf(newsTest))
        }

        viewModel = HeadlinesViewModel(repository)
    }

    @Test
    fun `insert and bookmark news return true`() {

        val bookmarkedNews = viewModel.headlines.getOrAwaitValue()

        print(bookmarkedNews.data)
    }
}
