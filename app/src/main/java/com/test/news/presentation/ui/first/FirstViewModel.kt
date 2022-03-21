package com.test.news.presentation.ui.first

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.test.news.data.net.Resource
import com.test.news.domain.model.News
import com.test.news.domain.usecase.NewsLocalUseCase
import com.test.news.domain.usecase.NewsRemoteUseCase
import com.test.news.presentation.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers

class FirstViewModel(
    private val newsRemoteUseCase: NewsRemoteUseCase,
    newsLocalUseCase: NewsLocalUseCase
) : BaseViewModel(newsLocalUseCase) {

    val visibleIsRefreshing = ObservableBoolean(false)
    val visibleProgressBar = ObservableBoolean(false)

    val newsFavoriteList: ArrayList<News.NewsItem> = ArrayList()
    val news = ObservableField<News>()
    val newsList: ArrayList<News.NewsItem> = ArrayList()

    val offSetData = MutableLiveData<String?>()
    val offSetNextData = MutableLiveData<String?>()

    val offSet = offSetData.switchMap {
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            emit(newsRemoteUseCase.getNews(it))
        }
    }

    val offSetNext = offSetNextData.switchMap {
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            emit(newsRemoteUseCase.getNews(it))
        }
    }
}