package com.newsapp.ui.main.headlines

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.newsapp.data.repositories.NewsRepository

class HeadlinesViewModel @ViewModelInject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    val headlines = repository.getHeadlines()
}
