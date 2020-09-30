package com.newsapp.ui.main.newsDetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.google.common.truth.Truth.assertThat
import com.newsapp.data.entities.News
import com.newsapp.data.entities.Source
import com.newsapp.data.repositories.NewsRepository
import com.newsapp.utils.getOrAwaitValue
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NewsDetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository: NewsRepository = mockk()
    private lateinit var viewModel: NewsDetailViewModel

    @Before
    fun `init repo and view model`() {
        viewModel = NewsDetailViewModel(repository)
    }

    @Test
    fun `get news by id return TRUE`() {
        val newsTest = News(
            1,
            Source(null, "testSource"),
            "titleTest",
            "descTest",
            "URL",
            "image",
            "2020-09-21",
            "contentTest",
            false
        )

        every { repository.getNewsById(newsTest.id!!) } returns MutableLiveData<News>().also {
            it.value = newsTest
        }

        val news = viewModel.getNewsById(newsTest.id!!).getOrAwaitValue()

        assertThat(news.id).isEqualTo(newsTest.id)
    }
}
