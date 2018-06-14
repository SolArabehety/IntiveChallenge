package com.solarabehety.intivechallenge

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient

/**
 * Created by Sol Arabehety on 6/12/2018.
 */
class IntiveApplication : Application() {
    init {
        instance = this
    }

    companion object {
        var instance: IntiveApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .build()
    }
}