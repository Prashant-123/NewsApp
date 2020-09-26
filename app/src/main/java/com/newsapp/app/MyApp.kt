package com.newsapp.app

import android.app.Application
import com.newsapp.BuildConfig
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree

@HiltAndroidApp
class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG) {
            plantTimber()
        }
    }

    private fun plantTimber() {
        Timber.plant(object : DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String? {
                return String.format(
                    "[L:%s] [M:%s] [C:%s]",
                    element.lineNumber,
                    element.methodName,
                    super.createStackElementTag(element)
                )
            }
        })
    }
}