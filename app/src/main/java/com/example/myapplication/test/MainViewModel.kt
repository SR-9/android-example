package com.example.myapplication.test

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.network.ApiService
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

@ActivityScoped
class MainViewModel @Inject constructor(
    @ActivityContext private val context: Context,
    private val apiService: ApiService
) {
    fun show() {
        println(context is AppCompatActivity)
        apiService.getPokemons().subscribeBy {  }
    }
}