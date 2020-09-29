package com.newsapp.ui.main.bookmarks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newsapp.data.repositories.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookmarksViewModelTest @ViewModelInject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    val bookmarks = repository.getBookmarkedNews()

    // Coroutine for making DB operations smooth
    fun bookmarkNews(id: Int, isBookmarked: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.bookmarkNews(id, isBookmarked)
        }
    }
}
