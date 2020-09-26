package com.newsapp.data.remote

import com.newsapp.models.NewsResponse
import com.newsapp.utils.ApiConstants
import retrofit2.Response
import retrofit2.http.GET

interface NewsAPI {

    @GET(ApiConstants.TOP_HEADLINES)
    suspend fun getHeadlines() : Response<NewsResponse>
}