package com.newsapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.newsapp.data.entities.News

@Dao
interface NewsDao {

    // Insert or Update
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertAll(news: List<News>)

    @Query("SELECT * FROM news order by publishedAt desc")
    fun getAllHeadlines() : LiveData<List<News>>

    @Query("SELECT * FROM news WHERE id = :id")
    fun getNewsById(id: Int) : LiveData<News>

    @Query("SELECT * FROM news WHERE isBookmarked = '1'")
    fun getBookmarkedNews() : LiveData<List<News>>

    @Query("UPDATE news SET isBookmarked = :isBookmarked WHERE id = :id")
    fun addBookmark(id: Int, isBookmarked: Int)
}