package com.test.news

import androidx.multidex.MultiDexApplication
import com.test.news.data.dataModules
import com.test.news.domain.domainModules
import com.test.news.presentation.presentationModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class NewsApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@NewsApplication)
            modules(dataModules + domainModules + presentationModules)
        }
    }
}