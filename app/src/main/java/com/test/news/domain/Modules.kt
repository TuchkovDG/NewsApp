package com.test.news.domain

import com.test.news.domain.usecase.NewsLocalUseCase
import com.test.news.domain.usecase.NewsRemoteUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { NewsRemoteUseCase(get()) }
    single { NewsLocalUseCase(get()) }
}

val domainModules = listOf(useCaseModule)