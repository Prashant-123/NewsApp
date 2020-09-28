package com.newsapp.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.newsapp.data.entities.News
import com.newsapp.data.local.NewsDao
import com.newsapp.data.remote.BaseResponse
import com.newsapp.data.remote.NewsAPI
import com.newsapp.data.remote.Resource
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsDao: NewsDao,
    private val newsAPI: NewsAPI
) : BaseResponse() {

    fun getHeadlines(): LiveData<Resource<List<News>>> = liveData(Dispatchers.IO) {
        emit(Resource.loading())

        val local = { newsDao.getAllHeadlines() }.invoke().map { Resource.success(it) }
        emitSource(local)

        val response = suspend { getApiResponse { newsAPI.getHeadlines() } }.invoke()

        if (response.status == Resource.Status.SUCCESS) {
            response.data?.articles?.let { newsDao.upsertAll(it) }
        }

        response.message?.let {
            emit(Resource.error(it))
            emitSource(local)
        }
    }

    fun getNewsById(newsId: Int): LiveData<News> = liveData(Dispatchers.IO) { emitSource(newsDao.getNewsById(id = newsId)) }

    fun getBookmarkedNews(): LiveData<List<News>> = liveData(Dispatchers.IO) { emitSource(newsDao.getBookmarkedNews()) }

    fun bookmarkNews(id: Int, isBookmarked: Int) = newsDao.addBookmark(id, isBookmarked)
}