package com.example.myapplication.test

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

//        DaggerApplicationComponent.builder()
//            .context(this)
//            .build()
    }

}