package com.test.news.data.net

import com.test.news.data.net.api.AppServerApi
import com.test.news.data.net.interceptor.QueryInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val TIMEOUT = 60L

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://alhapi.com/")
        .build()

fun provideForecastApi(retrofit: Retrofit): AppServerApi =
    retrofit.create(AppServerApi::class.java)

fun provideOkHttpClientLogging(
    loggingInterceptor: HttpLoggingInterceptor,
    queryInterceptor: QueryInterceptor
): OkHttpClient =
    OkHttpClient.Builder()
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(queryInterceptor)
        .build()

fun provideLoggingInterceptor(): HttpLoggingInterceptor =
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)