package com.test.news.presentation.ui.second

import com.test.news.domain.model.News
import com.test.news.domain.usecase.NewsLocalUseCase
import com.test.news.presentation.ui.base.BaseViewModel

class SecondViewModel(newsLocalUseCase: NewsLocalUseCase) : BaseViewModel(newsLocalUseCase) {

    val newsFavoriteList: ArrayList<News.NewsItem> = ArrayList()
}