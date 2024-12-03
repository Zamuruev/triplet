package com.triplet
import android.app.Application
import androidx.room.Room

class MyApplication : Application() {
    lateinit var database: AppDatabase
        private set
    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "characters_database"
        ).build()

        DataStoreManager.initialize(applicationContext)
    }
}
