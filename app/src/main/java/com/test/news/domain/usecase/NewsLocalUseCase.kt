package com.test.news.domain.usecase

import com.test.news.data.db.NewsDao
import com.test.news.domain.model.News
import com.test.news.domain.model.toNewsEntity
import com.test.news.domain.model.toNewsItem

class NewsLocalUseCase(private val newsDao: NewsDao) {

    suspend fun insert(news: News.NewsItem): Long = newsDao.insert(news.toNewsEntity())

    suspend fun deleteByUrl(url: String?): Int = newsDao.deleteByUrl(url)

    suspend fun getAllFavoriteNews(): List<News.NewsItem> {
        val list = newsDao.getAll().map { it.toNewsItem() }
        list.forEach {
            it.isFavorite = true
        }
        return list
    }
}