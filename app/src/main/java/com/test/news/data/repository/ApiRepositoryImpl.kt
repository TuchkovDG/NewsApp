package com.test.news.data.repository

import com.test.news.data.net.Resource
import com.test.news.data.net.ResponseHandler
import com.test.news.data.net.api.AppServerApi
import com.test.news.domain.model.News
import com.test.news.domain.model.toNews

class ApiRepositoryImpl(
    private val appServerApi: AppServerApi,
    private val responseHandler: ResponseHandler
) {

    suspend fun getNews(offset: String?): Resource<News> =
        try {
            val response = appServerApi.getNews(offset)
            responseHandler.handleSuccess(response.toNews())
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
}