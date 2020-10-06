package com.example.myapplication.di

import androidx.datastore.preferences.Preferences
import kotlinx.coroutines.flow.Flow


interface PrefDataStore {

    fun printSomething()

    fun getToken() : Flow<String?>

     suspend fun setToken(token: String) : Preferences
}

