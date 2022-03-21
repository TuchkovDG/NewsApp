package com.test.news.data.net.api

import com.test.news.data.dto.ResponseNews
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AppServerApi {

    @GET("v1/news")
    suspend fun getNews(@Query("offset") offset: String?): ResponseNews
}