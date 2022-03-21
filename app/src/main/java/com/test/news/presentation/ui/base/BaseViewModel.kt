package com.test.news.presentation.ui.base

import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.test.news.domain.model.News
import com.test.news.domain.usecase.NewsLocalUseCase
import kotlinx.coroutines.Dispatchers

open class BaseViewModel(newsLocalUseCase: NewsLocalUseCase) : ViewModel() {

    val insertPosition = ObservableInt()
    val deletePosition = ObservableInt()

    val getAllFavoriteNewsData = MutableLiveData<Int>()
    val insertNewsData = MutableLiveData<News.NewsItem>()
    val deleteNewsData = MutableLiveData<News.NewsItem>()

    val getAllFavoriteNews = getAllFavoriteNewsData.switchMap {
        liveData(Dispatchers.IO) {
            emit(newsLocalUseCase.getAllFavoriteNews())
        }
    }

    val insertNews = insertNewsData.switchMap {
        liveData(Dispatchers.IO) {
            emit(newsLocalUseCase.insert(it))
        }
    }

    val deleteNews = deleteNewsData.switchMap {
        liveData(Dispatchers.IO) {
            emit(newsLocalUseCase.deleteByUrl(it.url))
        }
    }
}