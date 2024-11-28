package com.triplet

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.edit
import java.io.File

object DataStoreManager {
    private lateinit var dataStore: DataStore<Preferences>

    fun initialize(context: Context) {
        dataStore = PreferenceDataStoreFactory.create(
            produceFile = { File(context.filesDir, "settings.preferences_pb") }
        )
    }

    fun getDataStore(): DataStore<Preferences> {
        return dataStore
    }
}
