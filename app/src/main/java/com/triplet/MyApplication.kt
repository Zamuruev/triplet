package com.triplet
import android.app.Application

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DataStoreManager.initialize(applicationContext)
    }
}
