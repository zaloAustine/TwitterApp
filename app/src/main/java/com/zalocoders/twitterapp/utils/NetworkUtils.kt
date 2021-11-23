package com.zalocoders.twitterapp.utils

import androidx.viewbinding.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor


val loggingInterceptor: HttpLoggingInterceptor
    get() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()

        httpLoggingInterceptor.apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
        return httpLoggingInterceptor
    }

