package com.test.news.data.net.interceptor

import com.test.news.presentation.internal.AppPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class QueryInterceptor(
    private val preferences: AppPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response =
        createNewBuilder(
            chain,
            preferences.language,
            preferences.countryCode,
            preferences.category,
            preferences.operatingSystem,
            preferences.version
        )

    private fun createNewBuilder(
        chain: Interceptor.Chain,
        language: String?,
        countryCode: String?,
        category: String?,
        operatingSystem: String?,
        version: String?,
    ): Response {
        val original: Request = chain.request()

        val url = original.url.newBuilder()
            .addQueryParameter("language", language)
            .addQueryParameter("countryCode", countryCode)
            .addQueryParameter("category", category)
            .addQueryParameter("os", operatingSystem)
            .addQueryParameter("v", version)
            .build()

        return chain.proceed(original.newBuilder().url(url).build())
    }
}