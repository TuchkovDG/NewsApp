package com.test.news.data

import androidx.room.Room
import com.test.news.data.db.AppDatabase
import com.test.news.data.net.*
import com.test.news.data.net.interceptor.QueryInterceptor
import com.test.news.data.repository.ApiRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "database-news"
        ).build()
    }
    single { get<AppDatabase>().newsDao() }
}

val networkModule = module {
    factory { QueryInterceptor(get()) }
    factory { provideLoggingInterceptor() }
    factory { ResponseHandler() }
}

val networkModuleClient = module {
    factory { provideOkHttpClientLogging(get(), get()) }
    factory { provideForecastApi(get()) }
    single { provideRetrofit(get()) }
}

val apiRepositoryModule = module {
    factory { ApiRepositoryImpl(get(), get()) }
}

val dataModules = listOf(dbModule, networkModule, networkModuleClient, apiRepositoryModule)