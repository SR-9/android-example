package com.example.myapplication.test

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class MainViewModel  @Inject constructor(
    @ActivityContext private val context: Context
) {
    fun show() {
       println(context is AppCompatActivity)
    }
}