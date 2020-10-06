package com.example.myapplication.di.impl

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import com.example.myapplication.di.PrefDataStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

class PrefDataStoreImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PrefDataStore {
    private val dataStore: DataStore<Preferences> = context.createDataStore("my_app")

    private val prefToken = preferencesKey<String>("token")

    override fun printSomething() {
        println(context.toString())
    }

    override fun getToken()  = dataStore.data.map { it[prefToken] }

     override suspend fun setToken(token: String) = dataStore.edit {
        it[prefToken] = token
    }
}


@Module
@InstallIn(ApplicationComponent::class)
abstract class PrefDataStoreModule {

    @Binds
    abstract fun bindAnalyticsService(
        analyticsServiceImpl: PrefDataStoreImpl
    ): PrefDataStore
}