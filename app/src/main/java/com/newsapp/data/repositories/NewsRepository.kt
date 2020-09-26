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
import timber.log.Timber
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

        Timber.d(response.data.toString())

        if (response.status == Resource.Status.SUCCESS) {
            response.data?.articles?.let { newsDao.upsertAll(it) }
        }

        response.message?.let {
            emit(Resource.error(it))
            emitSource(local)
        }
    }
}