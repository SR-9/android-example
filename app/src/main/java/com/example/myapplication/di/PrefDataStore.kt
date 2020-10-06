package com.example.myapplication.di

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.subscribe
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefDataStore @Inject constructor(@ApplicationContext private val context: Context) {
    private val dataStore: DataStore<Preferences> = context.createDataStore("my_app")

    private val prefToken = preferencesKey<String>("token")

    fun getToken()  = dataStore.data.map { it[prefToken] }

    suspend fun setToken(token: String) = dataStore.edit {
        it[prefToken] = token
    }
}