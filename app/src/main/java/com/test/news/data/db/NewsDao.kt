package com.test.news.data.db

import androidx.room.*

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(news: NewsEntity): Long

    @Delete
    suspend fun delete(news: NewsEntity): Int

    @Query("DELETE FROM newsentity WHERE url = :url")
    suspend fun deleteByUrl(url: String?): Int

    @Query("SELECT * FROM newsentity")
    suspend fun getAll(): List<NewsEntity>
}