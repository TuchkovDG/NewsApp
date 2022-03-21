package com.test.news.presentation

import com.test.news.presentation.internal.AppPreferences
import com.test.news.presentation.ui.MainViewModel
import com.test.news.presentation.ui.first.FirstViewModel
import com.test.news.presentation.ui.second.SecondViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appPrefModule = module {
    single { AppPreferences(androidContext()) }
}

val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel { FirstViewModel(get(), get()) }
    viewModel { SecondViewModel(get()) }
}

val presentationModules = listOf(appPrefModule, viewModelModule)