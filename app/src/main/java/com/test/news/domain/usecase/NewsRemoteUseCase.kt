package com.test.news.domain.usecase

import com.test.news.data.net.Resource
import com.test.news.data.repository.ApiRepositoryImpl
import com.test.news.domain.model.News

class NewsRemoteUseCase(private val apiRepo: ApiRepositoryImpl) {

    suspend fun getNews(offset: String?): Resource<News> = apiRepo.getNews(offset)
}