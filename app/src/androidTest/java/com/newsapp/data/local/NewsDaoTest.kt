package com.newsapp.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.newsapp.data.entities.News
import com.newsapp.data.entities.Source
import com.newsapp.utils.getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class NewsDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var appDatabase: AppDatabase
    private lateinit var newsDao: NewsDao

    @Before
    fun initDB() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()

        newsDao = appDatabase.newsDao()
    }

    @After
    fun destroyDB() {
        appDatabase.close()
    }

    @Test
    fun upsertAllTest_returnTrue() = runBlocking {
        val newsTest = News(
            1,
            Source("testSource", "testSource"), // Source must be same for Test Case bcz of Room Converters.kt
            "titleTest",
            "descTest",
            "URL",
            "image",
            "2020-09-21",
            "contentTest",
            false
        )
        val allTestNews = arrayListOf(newsTest)

        newsDao.upsertAllHeadlines(allTestNews)

        val insertedNews = newsDao.getAllHeadlines().getOrAwaitValueTest()
        assertThat(insertedNews).isEqualTo(allTestNews)
    }

    @Test
    fun upsertAllTest_returnFalse() = runBlocking {
        val newsTest = News(
            2,
            Source(null, "testSource"), // Source must be same for Test Case bcz of Room Converters.kt
            "titleTest",
            "descTest",
            "URL",
            "image",
            "2020-09-21",
            "contentTest",
            false
        )
        val allTestNews = arrayListOf(newsTest)

        newsDao.upsertAllHeadlines(allTestNews)

        val insertedNews = newsDao.getAllHeadlines().getOrAwaitValueTest()
        assertThat(insertedNews).isNotEqualTo(allTestNews)
    }

    @Test
    fun getBookmarkedNewsTest_returnTrue() {
        val newsTest = News(
            1,
            Source("testSource", "testSource"), // Source must be same for Test Case bcz of Room Converters.kt
            "titleTest",
            "descTest",
            "URL",
            "image",
            "2020-09-21",
            "contentTest",
            true
        )
        val allTestNews = arrayListOf(newsTest)

        newsDao.upsertAllHeadlines(allTestNews)

        val insertedNews = newsDao.getBookmarkedNews().getOrAwaitValueTest()
        assertThat(insertedNews).isEqualTo(allTestNews)
    }
}