package com.newsapp.ui.main.headlines

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsapp.data.repositories.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HeadlinesViewModel @ViewModelInject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    val headlines = repository.getHeadlines()

    fun bookmarkNews(id: Int, isBookmarked: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.bookmarkNews(id, isBookmarked)
        }
    }
}
