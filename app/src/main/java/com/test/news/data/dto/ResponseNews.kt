package com.test.news.data.dto

import com.google.gson.annotations.SerializedName

class ResponseNews(
    @SerializedName("news") val news: ArrayList<News>?,
    @SerializedName("offset") val offset: String?,
    @SerializedName("providerLabel") val providerLabel: String?
) {

    class News(
        @SerializedName("title") val title: String?,
        @SerializedName("plainTitle") val plainTitle: String?,
        @SerializedName("plainSubtitle") val plainSubtitle: String?,
        @SerializedName("img") val img: String?,
        @SerializedName("img_big") val img_big: String?,
        @SerializedName("url") val url: String?,
        @SerializedName("date") val date: Long?,
        @SerializedName("source_icon") val source_icon: String?,
        @SerializedName("source") val source: String?,
        @SerializedName("sponsored") val sponsored: Boolean?
    )
}