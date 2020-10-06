package com.example.myapplication.network

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET



interface ApiService {
    @GET("pokemon")
    fun getPokemons(): Observable<Any>
}