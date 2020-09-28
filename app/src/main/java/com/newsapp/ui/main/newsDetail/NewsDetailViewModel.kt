package com.newsapp.ui.main.newsDetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.newsapp.data.repositories.NewsRepository

class NewsDetailViewModel @ViewModelInject constructor(
    private val newsRepository: NewsRepository
) : ViewModel() {

    fun getNewsById(newsId: Int) = newsRepository.getNewsById(newsId)

//    fun bookmarkNews(newsId: Int) = newsRepository.bookmarkNews(newsId)

}