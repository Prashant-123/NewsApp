package com.newsapp.ui.main.headlines

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.google.common.truth.Truth.assertThat
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
    private lateinit var viewModel: HeadlinesViewModel

    @Before
    fun `init repo and view model`() {
        val newsTest = News(
            2,
            Source(null, "testSource"),
            "titleTest",
            "descTest",
            "URL",
            "image",
            "2020-09-21",
            "contentTest",
            false
        )

        every { repository.getHeadlines() } returns MutableLiveData<Resource<List<News>>>().also {
            it.value = Resource.success(listOf(newsTest))
        }

        // Add Bookmark
        every {
            repository.bookmarkNews(
                newsTest.id!!,
                1
            )
        } returns 1

        // Remove Bookmark
        every {
            repository.bookmarkNews(
                newsTest.id!!,
                0
            )
        } returns 0

        viewModel = HeadlinesViewModel(repository)
    }

    @Test
    fun `mock and test all news from view model status SUCCESS irrespective size`() {
        val bookmarkedNews = viewModel.headlines.getOrAwaitValue()
        assertThat(bookmarkedNews.status).isEqualTo(Resource.Status.SUCCESS)
    }

    @Test
    fun `mock and test all news from view model return list size`() {
        val bookmarkedNews = viewModel.headlines.getOrAwaitValue()
        assertThat(bookmarkedNews.data?.size).isGreaterThan(0)
    }

    @Test
    fun `add bookmark test isBookmarked TRUE`() {
        val rowsUpdated = viewModel.bookmarkNews(2, 1)
        assertThat(rowsUpdated).isEqualTo(rowsUpdated)
    }

    @Test
    fun `remove bookmark test isBookmarked FALSE`() {
        val rowsUpdated = viewModel.bookmarkNews(2, 0)
        assertThat(rowsUpdated).isEqualTo(rowsUpdated)
    }
}
