package com.newsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.newsapp.data.repositories.NewsRepository
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var newsRepository: NewsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newsRepository.getHeadlines().observe(this, {
            Timber.d(it.data.toString())
        })
    }
}