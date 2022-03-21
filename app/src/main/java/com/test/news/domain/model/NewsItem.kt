package com.test.news.domain.model

import com.test.news.data.db.NewsEntity
import com.test.news.data.dto.ResponseNews

data class News(
    val news: ArrayList<NewsItem>?,
    val offset: String?
) {

    data class NewsItem(
        val title: String?,
        val plainTitle: String?,
        val plainSubtitle: String?,
        val img: String?,
        val img_big: String?,
        val url: String?,
        val date: Long?,
        val source_icon: String?,
        val source: String?,
        val sponsored: Boolean?,
        var isFavorite: Boolean = false
    )
}

fun ResponseNews.toNews() = News(
    news = ArrayList(news?.map { it.toNewsItem() } ?: emptyList()),
    offset = offset
)

fun ResponseNews.News.toNewsItem() = News.NewsItem(
    title = title,
    plainTitle = plainTitle,
    plainSubtitle = plainSubtitle,
    img = img,
    img_big = img_big,
    url = url,
    date = date,
    source_icon = source_icon,
    source = source,
    sponsored = sponsored
)

fun NewsEntity.toNewsItem() = News.NewsItem(
    title = title,
    plainTitle = plainTitle,
    plainSubtitle = plainSubtitle,
    img = img,
    img_big = img_big,
    url = url,
    date = date,
    source_icon = source_icon,
    source = source,
    sponsored = sponsored
)

fun News.NewsItem.toNewsEntity() = NewsEntity(
    uid = null,
    title = title,
    plainTitle = plainTitle,
    plainSubtitle = plainSubtitle,
    img = img,
    img_big = img_big,
    url = url,
    date = date,
    source_icon = source_icon,
    source = source,
    sponsored = sponsored
)